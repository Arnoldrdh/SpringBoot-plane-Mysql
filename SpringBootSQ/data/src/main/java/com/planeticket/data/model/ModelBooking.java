package com.planeticket.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ModelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    private String seatNumber;
    private String status;
    private String bookingTime;
    private String paymentStatus;

    // fk
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private ModelFlight flight;

    // fk
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ModelUser user;

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public ModelFlight getFlight() {
        return flight;
    }

    public void setFlight(ModelFlight flight) {
        this.flight = flight;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer BookingId) {
        this.bookingId = BookingId;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingTime() {
        return this.bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String PaymentStatus) {
        this.paymentStatus = PaymentStatus;
    }

}
