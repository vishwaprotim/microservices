package com.protim.microservice.ps.api.service;

import com.protim.microservice.ps.api.entity.Payment;
import com.protim.microservice.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment){
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(processPayment());
        return repository.save(payment);
    }

    public String processPayment(){
        // 3rd party payment service call
        return new Random().nextBoolean()? "SUCCESS" : "FAILED";
    }

    public List<Payment> findPaymentByOrderId(String orderId){
        return repository.findByOrderId(Integer.parseInt(orderId));
    }

}
