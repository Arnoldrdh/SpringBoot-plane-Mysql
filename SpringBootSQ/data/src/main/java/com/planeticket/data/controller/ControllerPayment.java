package com.planeticket.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.PaymentRequest;
import com.planeticket.data.dto.PaymentResponseDTO;
import com.planeticket.data.service.ServicePayment;

@RestController
@RequestMapping("/payment")
public class ControllerPayment {
    @Autowired
    private ServicePayment srPayment;

    @PostMapping("/pay")
    public PaymentResponseDTO payBooking(@RequestBody PaymentRequest request) {

        return srPayment.payBooking(request);

    }

    @DeleteMapping("/delete/{paymentId}")
    public boolean deletePayment(@PathVariable Integer paymentId) {

        return srPayment.deletePayment(paymentId);
    }

}
