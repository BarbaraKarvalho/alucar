package com.barbara.alucar.presentation.dto;

import java.time.LocalDateTime;

public class RentalResponseDto {
    private String id;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String userName;
    private String email;
    private CarDto car;

    public RentalResponseDto() {
    }

    public RentalResponseDto(String id, LocalDateTime start, LocalDateTime finish, String userName, String email, CarDto car) {
        this.id = id;
        this.start = start;
        this.finish = finish;
        this.userName = userName;
        this.email = email;
        this.car = car;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }
}
