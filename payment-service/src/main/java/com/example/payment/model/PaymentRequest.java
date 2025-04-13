package com.example.payment.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private String fromUser;
    private String toUser;
    private double amount;
}
