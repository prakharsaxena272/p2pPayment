package com.example.payment.saga;

import com.example.payment.model.PaymentRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WalletToWalletSaga {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WalletToWalletSaga(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void execute(PaymentRequest request) {
        // Step 1: Debit source wallet
        kafkaTemplate.send("wallet-commands", "DEBIT:" + request.getFromUser() + ":" + request.getAmount());

        // Step 2: Credit destination wallet (normally you’d wait for success event before proceeding)
        kafkaTemplate.send("wallet-commands", "CREDIT:" + request.getToUser() + ":" + request.getAmount());

        // In real-world use state machine or workflow engine with rollback support
    }
}
