package com.xponential.onlinebooking;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.xponential.onlinebooking.controller.InitializeTablesController;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
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

        Deque<UUID> availableTables = new ArrayDeque<>();
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        ResponseEntity<InitializeTablesResponse> response = initializeTablesController.initializeTables(initializeTablesDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().getNumberOfTables().intValue());
    }

    @Test
    void testInitializeTablesAlreadyInitialized() {
        InitializeTablesDTO initializeTablesDTO = new InitializeTablesDTO();
        initializeTablesDTO.setNumberOfTables(BigDecimal.valueOf(5));

        Deque<UUID> availableTables = new ArrayDeque<>();
        when(bookingService.getAvailableTables()).thenReturn(availableTables);
        when(bookingService.isInitialized()).thenReturn(true);

        assertThrows(TablesAlreadyInitializedException.class, () -> initializeTablesController.initializeTables(initializeTablesDTO));
    }
}
