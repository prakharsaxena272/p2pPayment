package com.example.wallet.listener;

import com.example.wallet.dto.CreditRequest;
import com.example.wallet.dto.DebitRequest;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WalletCommandListener {

    private final WalletService walletService;

    public WalletCommandListener(WalletService walletService) {
        this.walletService = walletService;
    }

    @KafkaListener(topics = "wallet-commands", groupId = "wallet-group")
    public void listen(String message) {
        String[] parts = message.split(":");
        String type = parts[0];
        String userId = parts[1];
        double amount = Double.parseDouble(parts[2]);

        if ("DEBIT".equalsIgnoreCase(type)) {
            walletService.debit(new DebitRequest(userId, amount, "REF123"));
        } else if ("CREDIT".equalsIgnoreCase(type)) {
            walletService.credit(new CreditRequest(userId, amount, "REF123"));
        }
    }
}
