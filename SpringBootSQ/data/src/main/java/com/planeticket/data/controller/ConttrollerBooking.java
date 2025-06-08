package com.planeticket.data.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/booking")
public class ConttrollerBooking {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryFlight rpFlight;

    @Autowired
    private RepositoryUser rpUser;

    // create booking
    @PostMapping("/add")
    public BookingResponseDTO bookingFlight(@RequestBody BookingRequest reqBooking) {
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

         //response bagian flight dto
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(savedBooking.getBookingId());
        response.setSeatNumber(savedBooking.getSeatNumber());
        response.setPrice(flightData.getPrice());
        response.setStatus(savedBooking.getStatus());
        response.setBookingTime(savedBooking.getBookingTime());
        response.setPaymentStatus(savedBooking.getPaymentStatus());

         // 6. Buat DTO untuk flight
        FlightSummaryDTO flightDTO = new FlightSummaryDTO();
        flightDTO.setFlightNumber(flightData.getFlightNumber());
        flightDTO.setDeparture(flightData.getDeparture());
        flightDTO.setDestination(flightData.getDestination());
        response.setFlight(flightDTO);

        // 7. Buat DTO untuk user
        UserSummaryDTO userDTO = new UserSummaryDTO();
        userDTO.setUsername(userData.getUsername());
        userDTO.setEmail(userData.getEmail());
        response.setUser(userDTO);

        return response;
    }

    // ambil booking data berdasrkan id
    @GetMapping("/detail/{bookingId}")
    public ModelBooking bookingDetailId(@PathVariable Integer bookingId) {
        return rpBooking.findById(bookingId).orElse(null);
    }

    // endpoint update jadi cancel status tiket
    @PutMapping("/cancel/{bookingId}")
    public boolean bookingCancel(@PathVariable Integer bookingId) {
        ModelBooking booking = rpBooking.findById(bookingId).orElse(null);
        if (booking == null) {
            return false;
        }

        booking.setStatus("Cancelled");
        rpBooking.save(booking);
        return true;

    }

    // endpoint history pembelian tiket
    @GetMapping("/history/{userId}")
    public List<ModelBooking> historyBooking(@PathVariable Integer userId) {
        return rpBooking.findByUserUserId(userId);
    }

    // endpoint delete history tiket (payment dulu hapus baru ini)
    @DeleteMapping("/delete/{bookingId}")
    public boolean deleteBookingHistory(@PathVariable Integer bookingId) {
        if (rpBooking.existsById(bookingId)) {
            rpBooking.deleteById(bookingId);
            return true;
        }

        return false;

    }
}
