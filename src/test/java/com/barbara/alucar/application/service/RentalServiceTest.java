package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Rental;
import com.barbara.alucar.infrastructure.repository.CarRepository;
import com.barbara.alucar.infrastructure.repository.RentalRepository;
import com.barbara.alucar.presentation.dto.RentalStartRequestDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartRental() {
        RentalStartRequestDto dto = new RentalStartRequestDto("507f1f77bcf86cd799439011", "John Doe", "john@example.com");
        rentalService.startRental(dto);
        verify(rentalRepository, times(1)).persist(any(Rental.class));
    }

    @Test
    void testFinishRental() {
        Rental rental = new Rental();
        when(rentalRepository.findByIdOptional(new ObjectId("507f1f77bcf86cd799439012"))).thenReturn(Optional.of(rental));

        rentalService.finishRental("507f1f77bcf86cd799439012");

        verify(invoiceService, times(1)).process(rental);
    }
}