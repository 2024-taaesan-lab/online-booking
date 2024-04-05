package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class InitializeTablesController implements InitializeTablesApi {

    private boolean initialized;

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/initializeTables")
    public ResponseEntity<String> initializeTables(@RequestBody InitializeTablesDTO initializeTablesDTO) {

        if (!initialized) {
            // Initialization logic
            for (int i = 0; i < initializeTablesDTO.getNumberOfTables().intValue(); i++) {
                bookingService.getTables().put(UUID.randomUUID(), 4); // Each table initially has 4 seats
            }

            initialized = true;
            return ResponseEntity.ok("Tables initialized successfully.");
        } else {
            throw new TablesAlreadyInitializedException();
        }
    }
}
