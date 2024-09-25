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

        Payment paymentResponse = restTemplate.postForObject("http://payment-service/payment/doPayment", payment, Payment.class);
        // Notice how we have designed the url
        // Instead of using the actual URI http://localhost-9191 we have used http://payment-service
        // Thanks to Eureka Service Discovery, Order Service does not need to care about the host and port of
        // the payment service. The service name is ofcourse, case-sensitive, as with the URIs

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
