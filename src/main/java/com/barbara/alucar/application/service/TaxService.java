package com.barbara.alucar.application.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaxService {
    public double calculate(double value){
        return value * 0.20;
    }
}
