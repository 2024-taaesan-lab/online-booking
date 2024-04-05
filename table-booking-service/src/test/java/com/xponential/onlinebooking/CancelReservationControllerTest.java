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
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CancelReservationControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private CancelReservationController cancelReservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testCancelReservation() {
//        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
//        cancelReservationDTO.setBookingId(UUID.randomUUID());
//
//        when(bookingService.isInitialized()).thenReturn(true);
//        when(bookingService.cancelReservation(UUID.fromString(anyString()))).thenReturn(new CancelReservationResponse());
//
//        ResponseEntity<CancelReservationResponse> responseEntity = cancelReservationController.cancelReservation(cancelReservationDTO);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        verify(bookingService, times(1)).cancelReservation(UUID.fromString(anyString()));
//    }

    @Test
    void testCancelReservation_NotInitialized() {
        CancelReservationDTO cancelReservationDTO = new CancelReservationDTO();
        cancelReservationDTO.setBookingId(UUID.randomUUID());

        when(bookingService.isInitialized()).thenReturn(false);

        assertThrows(TablesNotInitializedException.class, () -> {
            cancelReservationController.cancelReservation(cancelReservationDTO);
        });

//        verify(bookingService, never()).cancelReservation(UUID.fromString(anyString()));
    }
}
