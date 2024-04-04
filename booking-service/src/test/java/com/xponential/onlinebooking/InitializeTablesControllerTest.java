package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.InitializeTablesController;
import com.xponential.onlinebooking.model.Conflict;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitializeTablesControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private InitializeTablesController initializeTablesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInitializeTables() {
        InitializeTablesDTO dto = new InitializeTablesDTO();
        dto.setNumberOfTables(BigDecimal.valueOf(5));
        ResponseEntity<Void> responseEntity = initializeTablesController.initializeTables(dto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testInitializeTablesWhenAlreadyInitialized() {
        InitializeTablesDTO dto = new InitializeTablesDTO();
        dto.setNumberOfTables(BigDecimal.valueOf(5));

        when(bookingService.getTables()).thenReturn(Collections.emptyList());

        initializeTablesController.initializeTables(dto); // Initialize once

        // Second initialization attempt should throw Conflict exception
        assertThrows(Conflict.class, () -> initializeTablesController.initializeTables(dto));
    }
}
