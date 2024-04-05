package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.CancelReservationController;
import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CancelReservationControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private CancelReservationController cancelReservationController;

    @BeforeEach
    void setUp() {
        cancelReservationController = new CancelReservationController();
        bookingService = mock(BookingService.class);
        cancelReservationController.setBookingService(bookingService);
    }

    @Test
    void testCancelReservationSuccess() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID();
        cancelReservationDTO.setBookingId(bookingId);

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
        for(int i=0; i < 8; i++) availableTables.add(UUID.randomUUID());
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        //Expected resevedMap
        Map<UUID, Deque> reservedTableMap = new HashMap<>();
        Deque<UUID> reservedTables = new ArrayDeque<>();
        reservedTables.add(UUID.randomUUID());
        reservedTableMap.put(bookingId, reservedTables);
        when(bookingService.getReservedTableMap()).thenReturn(reservedTableMap);
        when(bookingService.getReservedTables()).thenReturn(reservedTables);

        ResponseEntity<CancelReservationResponse> response = cancelReservationController.cancelReservation(cancelReservationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(9, response.getBody().getRemainingTables().intValue());
    }

    @Test
    void testCancelReservationTablesNotInitialized() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID();
        cancelReservationDTO.setBookingId(bookingId);

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
//        for(int i=0; i < 8; i++) availableTables.add(UUID.randomUUID());
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        // Call the API
        try {
            cancelReservationController.cancelReservation(cancelReservationDTO);
        } catch (TablesNotInitializedException e) {
            // Exception expected
            assertEquals("Tables not initialized", e.getMessage());
        }

    }

    @Test
    void testCancelReservationBookingIDNotFound() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID();
        cancelReservationDTO.setBookingId(bookingId);

        // Mock tables availability
        Deque<UUID> availableTables = new ArrayDeque<>();
        for(int i=0; i < 8; i++) availableTables.add(UUID.randomUUID());
        when(bookingService.getAvailableTables()).thenReturn(availableTables);

        //Expected resevedMap
        Map<UUID, Deque> reservedTableMap = new HashMap<>();
        Deque<UUID> reservedTables = new ArrayDeque<>();
        reservedTables.add(UUID.randomUUID());
        reservedTableMap.put(UUID.randomUUID(), reservedTables);
        when(bookingService.getReservedTableMap()).thenReturn(reservedTableMap);
        when(bookingService.getReservedTables()).thenReturn(reservedTables);

        // Call the API
        try {
            cancelReservationController.cancelReservation(cancelReservationDTO);
        } catch (BookingIDNotFoundException e) {
            // Exception expected
            assertEquals("Booking ID not found.", e.getMessage());
        }

    }
}
