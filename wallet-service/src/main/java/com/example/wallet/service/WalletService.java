package com.example.wallet.service;

import com.example.wallet.dto.CreditRequest;
import com.example.wallet.dto.DebitRequest;
import com.example.wallet.model.OutboxEvent;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.OutboxRepository;
import com.example.wallet.repository.WalletRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class WalletService {

    private final WalletRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxRepository outboxRepository;

    public WalletService(WalletRepository repository, KafkaTemplate<String, String> kafkaTemplate, OutboxRepository outboxRepository) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public void debit(DebitRequest request) {
        Wallet wallet = repository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < request.getAmount()) {
            // Handle insufficient balance
            String failedEvent = String.format(
                    "%s:DEBIT:%s:%.2f:FAILED",
                    request.getUserId(),
                    request.getReferenceId(),
                    request.getAmount()
            );
            kafkaTemplate.send("wallet-events", failedEvent);
            // Save to Outbox table
            OutboxEvent event = new OutboxEvent("WALLET_DEBITED", failedEvent);
            outboxRepository.save(event);
            throw new RuntimeException("Insufficient balance");
        }
        else {

            wallet.setBalance(wallet.getBalance() - request.getAmount());
            repository.save(wallet);

            // Create event for successful debit
            String successEvent = String.format(
                    "%s:DEBIT:%s:%.2f:SUCCESS",
                    request.getUserId(),
                    request.getReferenceId(),
                    request.getAmount()
            );

            // Save to Outbox table
            OutboxEvent event = new OutboxEvent("WALLET_DEBITED", successEvent);
            outboxRepository.save(event);

            // Publish event to Kafka
            kafkaTemplate.send("wallet-events", successEvent);
        }
    }

    @Transactional
    public void credit(CreditRequest request) {
        Wallet wallet = repository.findByUserId(request.getUserId())
                .orElse(new Wallet(request.getUserId(), 0.0));

        wallet.setBalance(wallet.getBalance() + request.getAmount());
        repository.save(wallet);

        // Create event for successful credit
        String successEvent = String.format(
                "%s:CREDIT:%s:%.2f:SUCCESS",
                request.getUserId(),
                request.getReferenceId(),
                request.getAmount()
        );

        // Save to Outbox table
        OutboxEvent event = new OutboxEvent("WALLET_CREDITED", successEvent);
        outboxRepository.save(event);

        // Publish event to Kafka
        kafkaTemplate.send("wallet-events", successEvent);
    }
}
