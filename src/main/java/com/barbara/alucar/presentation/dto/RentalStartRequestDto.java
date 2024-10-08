package com.barbara.alucar.presentation.dto;


public class RentalStartRequestDto {
    private String carId;
    private String userName;
    private String email;

    public RentalStartRequestDto() {
    }

    public RentalStartRequestDto(String carId, String userName, String email) {
        this.carId = carId;
        this.userName = userName;
        this.email = email;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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
}
