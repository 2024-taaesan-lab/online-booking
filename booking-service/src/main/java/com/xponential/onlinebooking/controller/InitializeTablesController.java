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

@RestController
@RequestMapping("/api/v1")
public class InitializeTablesController implements InitializeTablesApi {

    private boolean initialized;
    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/initializeTables")
    public ResponseEntity<Void> initializeTables(@RequestBody InitializeTablesDTO initializeTablesDTO) {

        if (!initialized) {
            // Initialization logic
            List<List<Integer>> tables = new ArrayList<>(bookingService.getTables());

            for (int i = 0; i < initializeTablesDTO.getNumberOfTables().intValue(); i++) {
                tables.add(new ArrayList<>());
            }

            bookingService.setTables(tables);
            initialized = true;
            return ResponseEntity.ok().build();
        } else {
            throw new TablesAlreadyInitializedException();
        }
    }
}
