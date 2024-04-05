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
        Map<UUID, Integer> tables = new HashMap<>();
        tables.put(bookingId, 4);
        when(bookingService.getTables()).thenReturn(tables);

        ResponseEntity<CancelReservationResponse> response = cancelReservationController.cancelReservation(cancelReservationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().getFreedTables().intValue());
        assertEquals(0, response.getBody().getRemainingTables().intValue());
//        verify(bookingService, times(1)).getTables();
    }

    @Test
    void testCancelReservationTablesNotInitialized() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID();
        cancelReservationDTO.setBookingId(bookingId);

        // Mock tables not initialized
        when(bookingService.getTables()).thenReturn(new HashMap<>());

        // Call the API
        try {
            cancelReservationController.cancelReservation(cancelReservationDTO);
        } catch (TablesNotInitializedException e) {
            // Exception expected
            assertEquals("Tables not initialized", e.getMessage());
        }

//        verify(bookingService, times(1)).getTables();
    }

    @Test
    void testCancelReservationBookingIDNotFound() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID();
        cancelReservationDTO.setBookingId(bookingId);

        // Mock tables availability
        Map<UUID, Integer> tables = new HashMap<>();
        tables.put(UUID.randomUUID(), 4); // Different booking ID
        when(bookingService.getTables()).thenReturn(tables);

        // Call the API
        try {
            cancelReservationController.cancelReservation(cancelReservationDTO);
        } catch (BookingIDNotFoundException e) {
            // Exception expected
            assertEquals("Booking ID not found.", e.getMessage());
        }

//        verify(bookingService, times(1)).getTables();
    }
}
