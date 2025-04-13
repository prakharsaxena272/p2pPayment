package com.example.wallet.controller;

import com.example.wallet.dto.CreditRequest;
import com.example.wallet.dto.DebitRequest;
import com.example.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/debit")
    public ResponseEntity<String> debit(@RequestBody DebitRequest request) {
        walletService.debit(request);
        return ResponseEntity.ok("Debited");
    }

    @PostMapping("/credit")
    public ResponseEntity<String> credit(@RequestBody CreditRequest request) {
        walletService.credit(request);
        return ResponseEntity.ok("Credited");
    }
}
