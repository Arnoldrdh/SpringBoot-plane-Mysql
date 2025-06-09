package com.planeticket.data.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.dto.BookingSummaryDTO;
import com.planeticket.data.dto.FlightSummaryDTO;
import com.planeticket.data.dto.PaymentRequest;
import com.planeticket.data.dto.PaymentResponseDTO;
import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.model.ModelPayment;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryPayment;

@Service
public class ServicePayment {
    @Autowired
    private RepositoryPayment rpPayment;

    @Autowired
    private RepositoryBooking rpBooking;

    // payment booking
    public PaymentResponseDTO payBooking(PaymentRequest request) {
        ModelBooking paymentBooking = rpBooking.findById(request.getBookingId()).orElse(null);

        if (paymentBooking == null) {
            throw new RuntimeException("Data tidak ditemukan");
        }

        Double ticketPrice = paymentBooking.getFlight().getPrice();

        if ("Paid".equalsIgnoreCase(paymentBooking.getPaymentStatus())) {
            throw new RuntimeException("sudah dibayar");
        }

        if (!request.getAmount().equals(ticketPrice)) {
            throw new RuntimeException("Harga Tidak Sesuai");
        }

        // data payment
        ModelPayment payment = new ModelPayment();
        payment.setPaymentStatus("Paid");
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setPaymentTime(LocalDateTime.now().toString());

        // set relasi ke booking
        payment.setBooking(paymentBooking);
        ModelPayment savedPayment = rpPayment.save(payment);

        // update di booking
        paymentBooking.setPaymentStatus("Paid");
        rpBooking.save(paymentBooking);

        // Build DTO response
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(savedPayment.getPaymentId());
        response.setPaymentMethod(savedPayment.getPaymentMethod());
        response.setPaymentStatus(savedPayment.getPaymentStatus());
        response.setPaymentTime(savedPayment.getPaymentTime());
        response.setAmount(savedPayment.getAmount());

        // Build Booking DTO
        BookingSummaryDTO bookingDTO = new BookingSummaryDTO();
        bookingDTO.setBookingId(paymentBooking.getBookingId());
        bookingDTO.setSeatNumber(paymentBooking.getSeatNumber());
        bookingDTO.setStatus(paymentBooking.getStatus());
        bookingDTO.setBookingTime(paymentBooking.getBookingTime());
        bookingDTO.setPaymentStatus(paymentBooking.getPaymentStatus());

        // Flight DTO
        ModelFlight flight = paymentBooking.getFlight();
        FlightSummaryDTO flightDTO = new FlightSummaryDTO();
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setDeparture(flight.getDeparture());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setDepartureTime(flight.getDepartureTime());
        flightDTO.setArrivalTime(flight.getArrivalTime());
        bookingDTO.setFlight(flightDTO);

        // User DTO
        ModelUser user = paymentBooking.getUser();
        UserSummaryDTO userDTO = new UserSummaryDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        bookingDTO.setUser(userDTO);

        response.setBooking(bookingDTO);
        return response;

    }

    // Delete Payment
    public boolean deletePayment(Integer paymentId) {
        if (rpPayment.existsById(paymentId)) {
            rpPayment.deleteById(paymentId);
            return true;
        }

        return false;
    }
}
