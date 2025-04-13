package com.example.wallet.dto;

import lombok.Data;

@Data
public class CreditRequest {
    private String userId;
    private double amount;
}
