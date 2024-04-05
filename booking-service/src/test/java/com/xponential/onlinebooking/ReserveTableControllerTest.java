package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.ReserveTableController;
import com.xponential.onlinebooking.model.InitializeTablesDTO;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
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

class ReserveTableControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ReserveTableController reserveTableController;

    @BeforeEach
    void setUp() {
        reserveTableController = new ReserveTableController();
        bookingService = mock(BookingService.class);
        reserveTableController.setBookingService(bookingService);
    }

    @Test
    void testReserveTablesSuccess() {

        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(7));

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
        for(int i=0; i < 8; i++) availableTables.add(UUID.randomUUID());
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        Deque<UUID> reservedTables = new ArrayDeque<>();
        when(bookingService.getReservedTables()).thenReturn(reservedTables);
        when(bookingService.isInitialized()).thenReturn(true);

        ResponseEntity<ReserveTableResponse> response = reserveTableController.reserveTables(reserveTableDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getBookedTables().intValue());
        assertEquals(6, response.getBody().getRemainingTables().intValue());
    }

    @Test
    void testReserveTablesNotInitialized() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(4));

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        // Call the API
        try {
            reserveTableController.reserveTables(reserveTableDTO);
        } catch (TablesNotInitializedException e) {
            // Exception expected
            assertEquals("Tables not initialized", e.getMessage());
        }
    }

    @Test
    void testReserveTablesNotEnoughTables() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(10));

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
        for(int i=0; i < 2; i++) {
            availableTables.add(UUID.randomUUID());
        }
        when(bookingService.isInitialized()).thenReturn(true);
        when(bookingService.getAvailableTables()).thenReturn(availableTables);
        // Call the API
        try {
            reserveTableController.reserveTables(reserveTableDTO);
        } catch (NotEnoughTablesForAllCustomersException e) {
            assertEquals("Not enough tables for all customers", e.getMessage());
        }
    }

}

