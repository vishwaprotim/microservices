package com.protim.microservice.os.api.common;

import com.protim.microservice.os.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Order order;
    private int amount;
    private String transactionId;
    private String message;
}
