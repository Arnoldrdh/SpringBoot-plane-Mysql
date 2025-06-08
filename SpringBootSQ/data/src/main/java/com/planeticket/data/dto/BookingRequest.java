package com.planeticket.data.dto;

public class BookingRequest {
    public String seatNumber;
    public Integer FlightId;
    public Integer UserId;

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getFlightId() {
        return this.FlightId;
    }

    public void setFlightId(Integer FlightId) {
        this.FlightId = FlightId;
    }

    public Integer getUserId() {
        return this.UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

}
