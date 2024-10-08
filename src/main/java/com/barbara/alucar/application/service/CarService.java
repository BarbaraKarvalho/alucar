package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Car;
import com.barbara.alucar.infrastructure.repository.CarRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;


@ApplicationScoped
public class CarService {

    @Inject
    private CarRepository carRepository;

    public void registerNewCar(Car car) throws IllegalArgumentException {
        boolean hasAnotherCarWithSamePlaca = carRepository.find("placa", car.getPlaca())
                .stream().findAny().isPresent();
        if (hasAnotherCarWithSamePlaca) {
            throw new IllegalArgumentException("There is another car with same placa");
        }
        Car carToAdd = Car.builder()
                .ano(2018)
                .marca("Ferrari")
                .modelo("sf90")
                .placa("FDB9WE43")
                .build();
        carRepository.persist(car);
    }

    public List<Car> findAll() {
        return carRepository.listAll();
    }
}
