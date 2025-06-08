package com.planeticket.data.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/payment")
public class ControllerPayment {
    @Autowired
    private RepositoryPayment rpPayment;

    @Autowired
    private RepositoryBooking rpBooking;

    @PostMapping("/pay")
    public PaymentResponseDTO payBooking(@RequestBody PaymentRequest request) {
        ModelBooking paymentBooking = rpBooking.findById(request.getBookingId()).orElse(null);

        if (paymentBooking == null) {
            throw new RuntimeException("Data tidak ditemukan");
        }

        if ("Paid".equalsIgnoreCase(paymentBooking.getPaymentStatus())) {
            throw new RuntimeException("sudah dibayar");
        }

        // data payment
        ModelPayment payment = new ModelPayment();
        payment.setPaymentStatus("Paid");
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setPaymentTime(LocalDateTime.now().toString());

        // set relasi ke booking
        payment.setBooking(paymentBooking);
        ModelPayment savedPayment =  rpPayment.save(payment);

        // update di booking
        paymentBooking.setPaymentStatus("Paid");
        rpBooking.save(paymentBooking);

        //  Build DTO response
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
        bookingDTO.setFlight(flightDTO);

        // User DTO
        ModelUser user = paymentBooking.getUser();
        UserSummaryDTO userDTO = new UserSummaryDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        bookingDTO.setUser(userDTO);

        response.setBooking(bookingDTO);
        return response;

        

    }

    @DeleteMapping("/delete/{paymentId}")
    public boolean deletePayment(@PathVariable Integer paymentId) {
        if (rpPayment.existsById(paymentId)) {
            rpPayment.deleteById(paymentId);
            return true;
        }

        return false;
    }

}
