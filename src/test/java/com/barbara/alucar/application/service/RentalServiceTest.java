package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Car;
import com.barbara.alucar.domain.entity.Rental;
import com.barbara.alucar.infrastructure.repository.CarRepository;
import com.barbara.alucar.infrastructure.repository.RentalRepository;
import com.barbara.alucar.presentation.dto.RentalResponseDto;
import com.barbara.alucar.presentation.dto.RentalStartRequestDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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

    private static final String VALID_OBJECT_ID = "507f1f77bcf86cd799439011";
    private static final String VALID_OBJECT_ID_2 = "507f1f77bcf86cd799439012";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class StartRental {

        @Test
        void shouldPersistRentalWithCorrectFields() {
            RentalStartRequestDto dto = new RentalStartRequestDto(VALID_OBJECT_ID, "John Doe", "john@example.com");

            rentalService.startRental(dto);

            ArgumentCaptor<Rental> captor = ArgumentCaptor.forClass(Rental.class);
            verify(rentalRepository, times(1)).persist(captor.capture());

            Rental persisted = captor.getValue();
            assertEquals(new ObjectId(VALID_OBJECT_ID), persisted.getCarId());
            assertEquals("John Doe", persisted.getUserName());
            assertEquals("john@example.com", persisted.getEmail());
            assertNotNull(persisted.getStart());
            assertNull(persisted.getFinish());
            assertNull(persisted.getInvoiceId());
        }

        @Test
        void shouldSetStartTimeCloseToNow() {
            RentalStartRequestDto dto = new RentalStartRequestDto(VALID_OBJECT_ID, "Jane", "jane@example.com");
            LocalDateTime before = LocalDateTime.now();

            rentalService.startRental(dto);

            LocalDateTime after = LocalDateTime.now();
            ArgumentCaptor<Rental> captor = ArgumentCaptor.forClass(Rental.class);
            verify(rentalRepository).persist(captor.capture());

            LocalDateTime startTime = captor.getValue().getStart();
            assertFalse(startTime.isBefore(before));
            assertFalse(startTime.isAfter(after));
        }

        @Test
        void shouldThrowExceptionForNullCarId() {
            RentalStartRequestDto dto = new RentalStartRequestDto(null, "John", "john@example.com");

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> rentalService.startRental(dto));
            assertTrue(ex.getMessage().contains("Invalid car ID format"));
            verify(rentalRepository, never()).persist(any(Rental.class));
        }

        @Test
        void shouldThrowExceptionForShortCarId() {
            RentalStartRequestDto dto = new RentalStartRequestDto("abc123", "John", "john@example.com");

            assertThrows(IllegalArgumentException.class, () -> rentalService.startRental(dto));
            verify(rentalRepository, never()).persist(any(Rental.class));
        }

        @Test
        void shouldThrowExceptionForNonHexCarId() {
            RentalStartRequestDto dto = new RentalStartRequestDto("zzzzzzzzzzzzzzzzzzzzzzzz", "John", "john@example.com");

            assertThrows(IllegalArgumentException.class, () -> rentalService.startRental(dto));
            verify(rentalRepository, never()).persist(any(Rental.class));
        }

        @Test
        void shouldThrowExceptionForEmptyCarId() {
            RentalStartRequestDto dto = new RentalStartRequestDto("", "John", "john@example.com");

            assertThrows(IllegalArgumentException.class, () -> rentalService.startRental(dto));
            verify(rentalRepository, never()).persist(any(Rental.class));
        }

        @Test
        void shouldAcceptUppercaseHexCarId() {
            RentalStartRequestDto dto = new RentalStartRequestDto("507F1F77BCF86CD799439011", "John", "john@example.com");

            rentalService.startRental(dto);

            verify(rentalRepository, times(1)).persist(any(Rental.class));
        }
    }

    @Nested
    class FinishRental {

        @Test
        void shouldSetFinishTimeAndProcessInvoice() {
            Rental rental = new Rental();
            rental.setStart(LocalDateTime.now().minusHours(3));
            when(rentalRepository.findByIdOptional(new ObjectId(VALID_OBJECT_ID_2)))
                    .thenReturn(Optional.of(rental));

            rentalService.finishRental(VALID_OBJECT_ID_2);

            assertNotNull(rental.getFinish());
            verify(invoiceService, times(1)).process(rental);
        }

        @Test
        void shouldSetFinishTimeCloseToNow() {
            Rental rental = new Rental();
            rental.setStart(LocalDateTime.now().minusHours(1));
            when(rentalRepository.findByIdOptional(new ObjectId(VALID_OBJECT_ID_2)))
                    .thenReturn(Optional.of(rental));

            LocalDateTime before = LocalDateTime.now();
            rentalService.finishRental(VALID_OBJECT_ID_2);
            LocalDateTime after = LocalDateTime.now();

            assertFalse(rental.getFinish().isBefore(before));
            assertFalse(rental.getFinish().isAfter(after));
        }

        @Test
        void shouldThrowExceptionWhenRentalNotFound() {
            when(rentalRepository.findByIdOptional(new ObjectId(VALID_OBJECT_ID_2)))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> rentalService.finishRental(VALID_OBJECT_ID_2));
            assertEquals("Rental not found", ex.getMessage());
            verify(invoiceService, never()).process(any());
        }

        @Test
        void shouldThrowExceptionForInvalidRentalId() {
            assertThrows(IllegalArgumentException.class,
                    () -> rentalService.finishRental("invalid-id"));
            verify(rentalRepository, never()).findByIdOptional(any());
            verify(invoiceService, never()).process(any());
        }

        @Test
        void shouldThrowExceptionForNullRentalId() {
            assertThrows(IllegalArgumentException.class,
                    () -> rentalService.finishRental(null));
            verify(rentalRepository, never()).findByIdOptional(any());
        }

        @Test
        void shouldThrowExceptionForEmptyRentalId() {
            assertThrows(IllegalArgumentException.class,
                    () -> rentalService.finishRental(""));
            verify(rentalRepository, never()).findByIdOptional(any());
        }

        @Test
        void shouldPassSameRentalInstanceToInvoiceService() {
            Rental rental = new Rental();
            rental.setStart(LocalDateTime.now().minusHours(5));
            when(rentalRepository.findByIdOptional(new ObjectId(VALID_OBJECT_ID_2)))
                    .thenReturn(Optional.of(rental));

            rentalService.finishRental(VALID_OBJECT_ID_2);

            ArgumentCaptor<Rental> captor = ArgumentCaptor.forClass(Rental.class);
            verify(invoiceService).process(captor.capture());
            assertSame(rental, captor.getValue());
        }
    }

    @Nested
    class FindAll {

        @Test
        void shouldReturnEmptyListWhenNoRentals() {
            when(rentalRepository.listAll()).thenReturn(Collections.emptyList());

            List<RentalResponseDto> result = rentalService.findAll();

            assertTrue(result.isEmpty());
        }

        @Test
        void shouldMapRentalFieldsToResponseDto() {
            ObjectId rentalId = new ObjectId(VALID_OBJECT_ID);
            ObjectId carId = new ObjectId(VALID_OBJECT_ID_2);
            LocalDateTime start = LocalDateTime.of(2026, 3, 1, 10, 0);
            LocalDateTime finish = LocalDateTime.of(2026, 3, 1, 15, 0);

            Rental rental = new Rental();
            rental.id = rentalId;
            rental.setCarId(carId);
            rental.setUserName("Maria");
            rental.setEmail("maria@example.com");
            rental.setStart(start);
            rental.setFinish(finish);

            Car car = new Car("ABC1234", "Civic", "Honda", 2024);
            car.id = carId;

            when(rentalRepository.listAll()).thenReturn(List.of(rental));
            when(carRepository.findById(carId)).thenReturn(car);

            List<RentalResponseDto> result = rentalService.findAll();

            assertEquals(1, result.size());
            RentalResponseDto dto = result.get(0);
            assertEquals(rentalId.toHexString(), dto.getId());
            assertEquals("maria@example.com", dto.getEmail());
            assertEquals(start, dto.getStart());
            assertEquals(finish, dto.getFinish());
            assertNotNull(dto.getCar());
            assertEquals(carId.toHexString(), dto.getCar().getId());
            assertEquals("Civic", dto.getCar().getModelo());
            assertEquals("Honda", dto.getCar().getMarca());
            assertEquals(2024, dto.getCar().getAno());
        }

        @Test
        void shouldMapMultipleRentals() {
            ObjectId carId = new ObjectId(VALID_OBJECT_ID_2);

            Rental rental1 = new Rental();
            rental1.id = new ObjectId();
            rental1.setCarId(carId);
            rental1.setUserName("User1");
            rental1.setEmail("user1@example.com");
            rental1.setStart(LocalDateTime.now());

            Rental rental2 = new Rental();
            rental2.id = new ObjectId();
            rental2.setCarId(carId);
            rental2.setUserName("User2");
            rental2.setEmail("user2@example.com");
            rental2.setStart(LocalDateTime.now());

            Car car = new Car("ABC1234", "Civic", "Honda", 2024);
            car.id = carId;

            when(rentalRepository.listAll()).thenReturn(List.of(rental1, rental2));
            when(carRepository.findById(carId)).thenReturn(car);

            List<RentalResponseDto> result = rentalService.findAll();

            assertEquals(2, result.size());
            verify(carRepository, times(2)).findById(carId);
        }

        @Test
        void shouldReturnNullFinishForActiveRental() {
            ObjectId carId = new ObjectId(VALID_OBJECT_ID_2);

            Rental rental = new Rental();
            rental.id = new ObjectId(VALID_OBJECT_ID);
            rental.setCarId(carId);
            rental.setUserName("Carlos");
            rental.setEmail("carlos@example.com");
            rental.setStart(LocalDateTime.now());
            rental.setFinish(null);

            Car car = new Car("XYZ9876", "Corolla", "Toyota", 2023);
            car.id = carId;

            when(rentalRepository.listAll()).thenReturn(List.of(rental));
            when(carRepository.findById(carId)).thenReturn(car);

            List<RentalResponseDto> result = rentalService.findAll();

            assertNull(result.get(0).getFinish());
        }
    }
}
