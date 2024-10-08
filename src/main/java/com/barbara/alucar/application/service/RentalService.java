package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Car;
import com.barbara.alucar.domain.entity.Rental;
import com.barbara.alucar.infrastructure.repository.CarRepository;
import com.barbara.alucar.infrastructure.repository.RentalRepository;
import com.barbara.alucar.presentation.dto.CarDto;
import com.barbara.alucar.presentation.dto.RentalResponseDto;
import com.barbara.alucar.presentation.dto.RentalStartRequestDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class RentalService {

    @Inject
    private RentalRepository rentalRepository;

    @Inject
    private CarRepository carRepository;

    @Inject
    private InvoiceService invoiceService;

    public List<RentalResponseDto> findAll(){
        return rentalRepository.listAll().stream().map(rental -> {
            RentalResponseDto rentalResponseDto = new RentalResponseDto();
            rentalResponseDto.setId(rental.id.toHexString());
            rentalResponseDto.setUserName(rentalResponseDto.getUserName());
            rentalResponseDto.setEmail(rental.getEmail());
            rentalResponseDto.setStart(rental.getStart());
            rentalResponseDto.setFinish(rental.getFinish());

            Car rentedCar = carRepository.findById(rental.getCarId());
            CarDto carDto = new CarDto();
            carDto.setId(rentedCar.id.toHexString());
            carDto.setAno(rentedCar.getAno());
            carDto.setMarca(rentedCar.getMarca());
            carDto.setModelo(rentedCar.getModelo());

            rentalResponseDto.setCar(carDto);
            return rentalResponseDto;
        })
            .toList();
    }

    public void startRental(RentalStartRequestDto rentalStartRequestDto) {
        var rentalToAdd = toRental(rentalStartRequestDto);
        rentalRepository.persist(rentalToAdd);
    }

    private Rental toRental(RentalStartRequestDto dto) {
        Rental rental = new Rental();
        rental.setCarId(new ObjectId(dto.getCarId()));
        rental.setUserName(dto.getUserName());
        rental.setEmail(dto.getEmail());
        rental.setStart(LocalDateTime.now());
        return rental;
    }

    public void finishRental(String id) {
        var rental = rentalRepository.findByIdOptional(new ObjectId(id))
                .orElseThrow(()-> new RuntimeException("Rental not founded"));

        rental.setFinish(LocalDateTime.now());
        invoiceService.process(rental);
    }
}
