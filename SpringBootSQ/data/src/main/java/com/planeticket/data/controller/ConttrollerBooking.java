package com.planeticket.data.controller;

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
import com.planeticket.data.service.ServiceBooking;

@RestController
@RequestMapping("/booking")
public class ConttrollerBooking {
    @Autowired
    private ServiceBooking srBooking;

    // create booking
    @PostMapping("/add")
    public BookingResponseDTO bookingFlight(@RequestBody BookingRequest reqBooking) {
        return srBooking.bookingFlight(reqBooking);
    }

    // ambil booking data berdasrkan id
    @GetMapping("/detail/{bookingId}")
    public BookingResponseDTO bookingDetailId(@PathVariable Integer bookingId) {
        return srBooking.bookingDetailId(bookingId);
    }

    // endpoint update jadi cancel status tiket
    @PutMapping("/cancel/{bookingId}")
    public boolean bookingCancel(@PathVariable Integer bookingId) {
        return srBooking.bookingCancel(bookingId);

    }

    // endpoint history pembelian tiket
    @GetMapping("/history/{userId}")
    public List<BookingResponseDTO> historyBooking(@PathVariable Integer userId) {
        return srBooking.historyBooking(userId);
    }

    // endpoint delete history tiket (payment dulu hapus baru ini)
    @DeleteMapping("/delete/{bookingId}")
    public boolean deleteBookingHistory(@PathVariable Integer bookingId) {
        return srBooking.deleteBookingHistory(bookingId);

    }
}
