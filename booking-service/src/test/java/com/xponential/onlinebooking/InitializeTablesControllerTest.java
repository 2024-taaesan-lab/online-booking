package com.xponential.onlinebooking;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.xponential.onlinebooking.controller.InitializeTablesController;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InitializeTablesControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private InitializeTablesController initializeTablesController;

    @BeforeEach
    void setUp() {
        initializeTablesController = new InitializeTablesController();
        bookingService = mock(BookingService.class);
        initializeTablesController.setBookingService(bookingService);
    }

    @Test
    void testInitializeTablesSuccess() {
        InitializeTablesDTO initializeTablesDTO = new InitializeTablesDTO();
        initializeTablesDTO.setNumberOfTables(BigDecimal.valueOf(5));

        ResponseEntity<String> response = initializeTablesController.initializeTables(initializeTablesDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tables initialized successfully.", response.getBody());
        verify(bookingService, times(5)).getTables();
    }

    @Test
    void testInitializeTablesAlreadyInitialized() {
        InitializeTablesDTO initializeTablesDTO = new InitializeTablesDTO();
        initializeTablesDTO.setNumberOfTables(BigDecimal.valueOf(5));

        // Simulate tables already initialized
        initializeTablesController.initializeTables(initializeTablesDTO);

        // Second initialization attempt should throw Conflict exception
        assertThrows(TablesAlreadyInitializedException.class, () -> initializeTablesController.initializeTables(initializeTablesDTO));

//        verify(bookingService, times(1)).getTables(); // Verify that bookingService.getTables() is only called once
    }
}
