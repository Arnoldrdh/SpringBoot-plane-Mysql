package com.planeticket.data.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.dto.BookingRequest;
import com.planeticket.data.dto.BookingResponseDTO;
import com.planeticket.data.dto.FlightSummaryDTO;
import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryFlight;
import com.planeticket.data.repository.RepositoryUser;

@Service
public class ServiceBooking {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryUser rpUser;

    @Autowired
    private RepositoryFlight rpFlight;

    // create booking
    public BookingResponseDTO bookingFlight(BookingRequest reqBooking) {
        ModelBooking booking = new ModelBooking();

        // ambil data penerbangan
        ModelFlight flightData = rpFlight.findById(reqBooking.getFlightId()).orElse(null);

        if (flightData == null) {
            throw new RuntimeException("Penerbangan tidak ditemukan");
        }

        // ambil data user
        ModelUser userData = rpUser.findById(reqBooking.getUserId()).orElse(null);

        // set relasi flight dan user ke booking
        booking.setFlight(flightData);
        booking.setUser(userData);

        // set nilai default
        booking.setStatus("Booked");
        booking.setPaymentStatus("Unpaid");
        booking.setBookingTime(LocalDateTime.now().toString());
        booking.setSeatNumber(reqBooking.getSeatNumber());

        ModelBooking savedBooking = rpBooking.save(booking);

        // response bagian flight dto
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(savedBooking.getBookingId());
        response.setSeatNumber(savedBooking.getSeatNumber());
        response.setPrice(flightData.getPrice());
        response.setStatus(savedBooking.getStatus());
        response.setBookingTime(savedBooking.getBookingTime());
        response.setPaymentStatus(savedBooking.getPaymentStatus());

        // Buat DTO untuk flight
        FlightSummaryDTO flightDTO = new FlightSummaryDTO();
        flightDTO.setFlightNumber(flightData.getFlightNumber());
        flightDTO.setDeparture(flightData.getDeparture());
        flightDTO.setDestination(flightData.getDestination());
        flightDTO.setArrivalTime(flightData.getArrivalTime());
        flightDTO.setDepartureTime(flightData.getDepartureTime());
        response.setFlight(flightDTO);

        // Buat DTO untuk user
        UserSummaryDTO userDTO = new UserSummaryDTO();
        userDTO.setUsername(userData.getUsername());
        userDTO.setEmail(userData.getEmail());
        userDTO.setPhoneNumber(userData.getPhoneNumber());
        response.setUser(userDTO);

        return response;
    }

    // get booking by id
    public BookingResponseDTO bookingDetailId(Integer bookingId) {
        ModelBooking booking = rpBooking.findById(bookingId).orElse(null);
        if (booking == null)
            return null;

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setSeatNumber(booking.getSeatNumber());
        dto.setStatus(booking.getStatus());
        dto.setBookingTime(booking.getBookingTime().toString());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setPrice(booking.getFlight().getPrice());

        FlightSummaryDTO flightDTO = new FlightSummaryDTO();
        flightDTO.setFlightNumber(booking.getFlight().getFlightNumber());
        flightDTO.setDeparture(booking.getFlight().getDeparture());
        flightDTO.setDestination(booking.getFlight().getDestination());
        flightDTO.setDepartureTime(booking.getFlight().getDepartureTime().toString());
        flightDTO.setArrivalTime(booking.getFlight().getArrivalTime().toString());
        dto.setFlight(flightDTO);

        // Map user
        UserSummaryDTO userDTO = new UserSummaryDTO();
        userDTO.setUsername(booking.getUser().getUsername());
        userDTO.setEmail(booking.getUser().getEmail());
        userDTO.setPhoneNumber(booking.getUser().getPhoneNumber());
        dto.setUser(userDTO);

        return dto;
    }

    // update status booking ke cancel
    public boolean bookingCancel(Integer bookingId) {
        ModelBooking booking = rpBooking.findById(bookingId).orElse(null);
        if (booking == null) {
            return false;
        }

        // Kembalikan kursi ke ModelFlight
        ModelFlight flight = booking.getFlight();
        if (flight != null) {
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            rpFlight.save(flight);
        }

        booking.setStatus("Cancelled");
        rpBooking.save(booking);
        return true;

    }

    // history pemesanan
    public List<BookingResponseDTO> historyBooking(Integer userId) {
        List<ModelBooking> bookings = rpBooking.findByUserUserId(userId);
        List<BookingResponseDTO> responseList = new ArrayList<>();

        for (ModelBooking booking : bookings) {
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(booking.getBookingId());
            dto.setSeatNumber(booking.getSeatNumber());
            dto.setStatus(booking.getStatus());
            dto.setBookingTime(booking.getBookingTime());
            dto.setPaymentStatus(booking.getPaymentStatus());

            // Flight
            ModelFlight flight = booking.getFlight();
            if (flight != null) {
                FlightSummaryDTO flightDTO = new FlightSummaryDTO();
                flightDTO.setFlightNumber(flight.getFlightNumber());
                flightDTO.setDeparture(flight.getDeparture());
                flightDTO.setDestination(flight.getDestination());
                flightDTO.setDepartureTime(flight.getDepartureTime());
                flightDTO.setArrivalTime(flight.getArrivalTime());
                dto.setFlight(flightDTO);

                dto.setPrice(flight.getPrice()); // Set harga dari flight
            }

            // User
            ModelUser user = booking.getUser();
            if (user != null) {
                UserSummaryDTO userDTO = new UserSummaryDTO();
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                dto.setUser(userDTO);
            }

            responseList.add(dto);
        }

        return responseList;
    }

    // delete booking(hapus payment)
    public boolean deleteBookingHistory(Integer bookingId) {
        if (rpBooking.existsById(bookingId)) {
            rpBooking.deleteById(bookingId);
            return true;
        }

        return false;

    }
}
