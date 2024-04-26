package com.demo.onlinebooking;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.demo.onlinebooking.controller.InitializeTablesController;
import com.demo.onlinebooking.model.InitializeTablesDTO;
import com.demo.onlinebooking.model.InitializeTablesResponse;
import com.demo.onlinebooking.service.TableReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InitializeTablesControllerTest {
    @Mock
    private TableReservationService tableReservationService;

    @InjectMocks
    private InitializeTablesController initializeTablesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void initializeTables_ValidRequest_Success() {
        // Mocking behavior of TableReservationService
        InitializeTablesDTO request = new InitializeTablesDTO();
        request.setNumberOfTables(BigDecimal.valueOf(5)); // Example number of tables
        InitializeTablesResponse expectedResponse = new InitializeTablesResponse();
        // Assuming expected response setup here

        when(tableReservationService.initializeTables(anyInt())).thenReturn(expectedResponse);

        // Invoking controller method
        ResponseEntity<InitializeTablesResponse> responseEntity = initializeTablesController.initializeTables(request);

        // Verifying response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
