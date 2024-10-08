package com.barbara.alucar.presentation.controller;

import com.barbara.alucar.application.service.RentalService;
import com.barbara.alucar.presentation.dto.RentalResponseDto;
import com.barbara.alucar.presentation.dto.RentalStartRequestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/api/v1/rentals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RentalController {

    @Inject
    private RentalService rentalService;

    @GET
    public Response listAllRentals(){
        List<RentalResponseDto> rentals = rentalService.findAll();
        return Response.status(200).entity(rentals).build();
    }

    @POST
    @Path("/start")
    public Response startRental(RentalStartRequestDto rentalStartRequestDto){
        rentalService.startRental(rentalStartRequestDto);
        return Response.status(201).build();
    }

    @PATCH
    @Path("/finish/{id}")
    public Response finishRental(@PathParam("id") String id){
        rentalService.finishRental(id);
        return Response.status(200).build();
    }
}
