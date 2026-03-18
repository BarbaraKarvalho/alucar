package com.barbara.alucar.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidatePaymentMethod() {
        assertTrue(paymentService.validatePaymentMethod("Credit Card"));
        assertFalse(paymentService.validatePaymentMethod(null));
        assertFalse(paymentService.validatePaymentMethod(""));
    }

    @Test
    void testProcessPayment() {
        assertDoesNotThrow(() -> paymentService.processPayment(100.0));
    }

    @Test
    void testRecordPayment() {
        assertDoesNotThrow(() -> paymentService.recordPayment("TX123", 100.0));
    }
}