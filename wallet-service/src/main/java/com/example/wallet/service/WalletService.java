package com.example.wallet.service;

import com.example.wallet.dto.CreditRequest;
import com.example.wallet.dto.DebitRequest;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WalletService(WalletRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void debit(DebitRequest request) {
        Wallet wallet = repository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - request.getAmount());
        repository.save(wallet);
        kafkaTemplate.send("wallet-events", "WALLET_DEBITED:" + request.getUserId());
    }

    public void credit(CreditRequest request) {
        Wallet wallet = repository.findByUserId(request.getUserId())
                .orElse(new Wallet(request.getUserId(), 0.0));
        wallet.setBalance(wallet.getBalance() + request.getAmount());
        repository.save(wallet);
        kafkaTemplate.send("wallet-events", "WALLET_CREDITED:" + request.getUserId());
    }
}
