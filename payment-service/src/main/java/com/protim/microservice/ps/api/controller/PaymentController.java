package com.protim.microservice.ps.api.controller;


import com.protim.microservice.ps.api.entity.Payment;
import com.protim.microservice.ps.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment){
        return service.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public List<Payment> findPaymentByOrderId(@PathVariable String orderId){
        return service.findPaymentByOrderId(orderId);
    }
}
