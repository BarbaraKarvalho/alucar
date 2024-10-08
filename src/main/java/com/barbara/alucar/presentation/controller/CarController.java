package com.barbara.alucar.presentation.controller;

import com.barbara.alucar.application.service.CarService;
import com.barbara.alucar.domain.entity.Car;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/cars")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarController {

    @Inject
    private CarService carService;

    @POST
    public Response createCar(Car car){
        try{
            carService.registerNewCar(car);
            return Response.status(201).entity(car).build();
        } catch (IllegalArgumentException e) {
            return Response.status(409).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response listAllCars(){
        List<Car> cars = carService.findAll();
        return Response.status(200).entity(cars).build();
    }
}
