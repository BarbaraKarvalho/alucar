package com.barbara.alucar.application.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {

    public boolean validatePaymentMethod(String paymentMethod) {
        return paymentMethod != null && !paymentMethod.isEmpty();
    }

    public void processPayment(double amount) {
        System.out.println("Payment processed: $" + amount);
    }

    public void recordPayment(String transactionId, double amount) {
        System.out.println("Payment recorded: Transaction ID: " + transactionId + ", Amount: $" + amount);
    }
}