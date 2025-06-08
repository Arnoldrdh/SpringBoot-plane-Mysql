package com.planeticket.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ModelPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentId;

    private String paymentMethod;
    private String paymentStatus;
    private String paymentTime;
    private double amount;

    // fk
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private ModelBooking booking;

    public ModelBooking getBooking() {
        return booking;
    }

    public void setBooking(ModelBooking booking) {
        this.booking = booking;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer PaymentId) {
        this.paymentId = PaymentId;
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

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
