package com.barbara.alucar.application.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaxService {
    public double calculate(double value){
        if(value > 100.0)
            return 0.15 * value;

        return value * 0.20;
    }
}
