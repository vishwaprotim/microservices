package com.protim.microservice.os.api.service;

import com.protim.microservice.os.api.common.Payment;
import com.protim.microservice.os.api.common.TransactionRequest;
import com.protim.microservice.os.api.common.TransactionResponse;
import com.protim.microservice.os.api.entity.Order;
import com.protim.microservice.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest request){
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);
        assert paymentResponse != null;
        String message = paymentResponse.getPaymentStatus().equals("SUCCESS") ? "Payment successful!" : "Payment failed due to errors in payment API";
        Order orderResponse = repository.save(order);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setOrder(orderResponse);
        transactionResponse.setMessage(message);
        transactionResponse.setTransactionId(paymentResponse.getTransactionId());
        return transactionResponse;
    }
}
