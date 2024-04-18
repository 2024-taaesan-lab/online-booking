package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.CancelReservationController;
import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.service.TableReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CancelReservationControllerTest {


    @Mock
    private TableReservationService tableReservationService;

    @InjectMocks
    private CancelReservationController cancelReservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void cancelReservation_ValidRequest_Success() throws Exception {
        // Mocking behavior of TableReservationService
        CancelReservationDTO request = new CancelReservationDTO();
        UUID bookingId = UUID.randomUUID(); // Example bookingId
        request.setBookingId(bookingId.toString());
        CancelReservationResponse expectedResponse = new CancelReservationResponse();
        // Assuming expected response setup here

        when(tableReservationService.cancelReservation(any(String.class))).thenReturn(expectedResponse);

        // Invoking controller method
        ResponseEntity<CancelReservationResponse> responseEntity = cancelReservationController.cancelReservation(request);

        // Verifying response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
