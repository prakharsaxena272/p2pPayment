package com.example.wallet.dto;

import lombok.Data;

@Data
public class CreditRequest {
        private String userId;
        private Double amount;
        private String referenceId; // <- add this

    public CreditRequest(String userId, double amount, String ref123) {
        this.userId = userId;
        this.amount = amount;
        this.referenceId = ref123;
    }

    // Getters & setters
    }
