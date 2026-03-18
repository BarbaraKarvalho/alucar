package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Invoice;
import com.barbara.alucar.domain.entity.Rental;
import com.barbara.alucar.infrastructure.repository.InvoiceRepository;
import com.barbara.alucar.infrastructure.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private TaxService taxService;

    @Mock
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess() {
        Rental rental = new Rental(LocalDateTime.now(), LocalDateTime.now().plusHours(5), null, null, "John Doe", "john@example.com");
        when(taxService.calculate(anyDouble())).thenReturn(10.0);

        invoiceService.process(rental);

        verify(invoiceRepository, times(1)).persist(any(Invoice.class));
        verify(rentalRepository, times(1)).update(rental);
    }
}