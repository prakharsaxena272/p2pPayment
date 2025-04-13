package com.example.wallet.dto;

import lombok.Data;

@Data
public class DebitRequest {
    private String userId;
    private double amount;
}
