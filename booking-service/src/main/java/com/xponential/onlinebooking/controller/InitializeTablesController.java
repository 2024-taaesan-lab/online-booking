package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class InitializeTablesController implements InitializeTablesApi {
    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/initializeTables")
    public ResponseEntity<InitializeTablesResponse> initializeTables(@RequestBody InitializeTablesDTO initializeTablesDTO) {

        if (!bookingService.isInitialized()) {
            // Initialization logic
            for (int i = 0; i < initializeTablesDTO.getNumberOfTables().intValue(); i++) {
                bookingService.getAvailableTables().add(UUID.randomUUID()); // Each table initially has 4 seats
            }

            bookingService.setInitialized(true);
            InitializeTablesResponse response = new InitializeTablesResponse();
            response.setNumberOfTables(BigDecimal.valueOf(bookingService.getAvailableTables().size()));
            return ResponseEntity.ok(response);
        } else {
            throw new TablesAlreadyInitializedException();
        }
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
