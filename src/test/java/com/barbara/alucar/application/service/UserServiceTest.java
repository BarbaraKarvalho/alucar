package com.barbara.alucar.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        assertDoesNotThrow(() -> userService.registerUser("John Doe", "john@example.com"));
    }

    @Test
    void testGetUserDetails() {
        String details = userService.getUserDetails("john@example.com");
        assertTrue(details.contains("john@example.com"));
    }
<<<<<<< Updated upstream
=======

    @Test
    void testUpdateUser() {
        assertDoesNotThrow(() -> userService.updateUser("john@example.com", "John Smith"));
    }
    
    @test
    void testDeleteUser() {
        assertDoesNotThrow(() -> userService.deleteUser(""));
    }
    
>>>>>>> Stashed changes
}