package com.example.payment.controller;

import com.example.payment.model.PaymentRequest;
import com.example.payment.service.PaymentOrchestratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentOrchestratorService orchestratorService;

    public PaymentController(PaymentOrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest request) {
        orchestratorService.startSaga(request);
        return ResponseEntity.ok("Payment initiated");
    }
}
