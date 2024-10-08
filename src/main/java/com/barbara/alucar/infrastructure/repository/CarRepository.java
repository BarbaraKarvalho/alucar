package com.barbara.alucar.infrastructure.repository;

import com.barbara.alucar.domain.entity.Car;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CarRepository implements PanacheMongoRepository<Car> {
}
