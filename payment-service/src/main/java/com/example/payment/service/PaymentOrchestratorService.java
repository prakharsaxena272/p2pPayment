package com.example.payment.service;

import com.example.payment.model.PaymentRequest;
import com.example.payment.saga.WalletToWalletSaga;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrchestratorService {

    private final WalletToWalletSaga saga;

    public PaymentOrchestratorService(WalletToWalletSaga saga) {
        this.saga = saga;
    }

    public void startSaga(PaymentRequest request) {
        saga.execute(request);
    }
}
