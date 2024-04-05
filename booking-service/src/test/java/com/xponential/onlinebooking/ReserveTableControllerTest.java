package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.ReserveTableController;
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
import java.util.HashMap;
import java.util.Map;
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
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(8));

        // Mock tables availability
        Map<UUID, Integer> tables = new HashMap<>();
        tables.put(UUID.randomUUID(), 4);
        tables.put(UUID.randomUUID(), 4);
        when(bookingService.getTables()).thenReturn(tables);

        ResponseEntity<ReserveTableResponse> response = reserveTableController.reserveTables(reserveTableDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getBookedTables().intValue());
        assertEquals(0, response.getBody().getRemainingTables().intValue());
//        verify(bookingService, times(1)).getTables();
    }

    @Test
    void testReserveTablesNotInitialized() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(4));

        // Mock tables not initialized
        when(bookingService.getTables()).thenReturn(new HashMap<>());

        // Call the API
        try {
            reserveTableController.reserveTables(reserveTableDTO);
        } catch (TablesNotInitializedException e) {
            // Exception expected
            assertEquals("Tables not initialized", e.getMessage());
        }

        verify(bookingService, times(1)).getTables();
    }

    @Test
    void testReserveTablesNotEnoughTables() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(10));

        // Mock tables availability
        Map<UUID, Integer> tables = new HashMap<>();
        tables.put(UUID.randomUUID(), 4);
        when(bookingService.getTables()).thenReturn(tables);

        // Call the API
        try {
            reserveTableController.reserveTables(reserveTableDTO);
        } catch (NotEnoughTablesForAllCustomersException e) {
            // Exception expected
            assertEquals("Not enough tables for all customers", e.getMessage());
        }

//        verify(bookingService, times(1)).getTables();
    }

}

