package com.example.transaction.listener;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

    private final TransactionRepository repository;

    public TransactionEventListener(TransactionRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "wallet-events", groupId = "transaction-group")
    public void listen(String message) {
        // Expecting userId:type:referenceId:amount:status
        try {
            String[] parts = message.split(":");
            Transaction txn = new Transaction();
            txn.setUserId(parts[0]);
            txn.setType(parts[1]);
            txn.setReferenceId(parts[2]);
            txn.setAmount(Double.parseDouble(parts[3]));
            txn.setStatus(parts[4]);
            repository.save(txn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
