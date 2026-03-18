package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Car;
import com.barbara.alucar.infrastructure.repository.CarRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewCar() {
        Car car = new Car("ABC123", "Model X", "Tesla", 2022);
        PanacheQuery<Car> mockQuery = mock(PanacheQuery.class);
        when(mockQuery.list()).thenReturn(List.of());
        when(carRepository.find("placa", car.getPlaca())).thenReturn(mockQuery);

        assertDoesNotThrow(() -> carService.registerNewCar(car));
        verify(carRepository, times(1)).persist(car);
    }

    @Test
    void testFindAll() {
        List<Car> cars = List.of(new Car("ABC123", "Model X", "Tesla", 2022));
        when(carRepository.listAll()).thenReturn(cars);

        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
        assertEquals("ABC123", result.get(0).getPlaca());
    }
}