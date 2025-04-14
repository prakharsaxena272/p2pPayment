package com.example.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aggregateId;
    private String eventType;
    private String eventData;
    private String status = "PENDING";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public OutboxEvent(String walletDebited, String format) {
        this.aggregateId = walletDebited;
        this.eventType = format;
    }

    // Getters and setters
}
