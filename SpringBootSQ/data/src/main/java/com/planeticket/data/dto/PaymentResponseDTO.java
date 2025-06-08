package com.planeticket.data.dto;

public class PaymentResponseDTO {
    private Integer paymentId;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTime;
    private Double amount;
    private BookingSummaryDTO booking;

    public Integer getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTime() {
        return this.paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BookingSummaryDTO getBooking() {
        return this.booking;
    }

    public void setBooking(BookingSummaryDTO booking) {
        this.booking = booking;
    }

}
