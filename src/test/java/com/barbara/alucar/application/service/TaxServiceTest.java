package com.barbara.alucar.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TaxServiceTest {

    @InjectMocks
    private TaxService taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculate() {
        assertEquals(20.0, taxService.calculate(100.0), 0.1); 
        assertEquals(20.0, taxService.calculate(100.1), 0.1); 
    }
}