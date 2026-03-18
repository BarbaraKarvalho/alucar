package com.barbara.alucar.application.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

    public void registerUser(String name, String email) {
        System.out.println("User registered: " + name + ", " + email);
    }

    public String getUserDetails(String email) {
        return "Details for user with email: " + email;
    }

    public void updateUser(String email, String newName) {
        System.out.println("User updated: " + email + " to name: " + newName);
    }
}