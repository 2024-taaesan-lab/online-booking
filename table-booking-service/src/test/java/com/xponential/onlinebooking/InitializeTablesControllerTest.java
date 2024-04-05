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
import org.mockito.MockitoAnnotations;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInitializeTables() {
        InitializeTablesDTO initializeTablesDTO = new InitializeTablesDTO();
        initializeTablesDTO.setNumberOfTables(BigDecimal.TEN);

        when(bookingService.isInitialized()).thenReturn(false);
        when(bookingService.initializeTables(anyInt())).thenReturn(new InitializeTablesResponse());

        ResponseEntity<InitializeTablesResponse> responseEntity = initializeTablesController.initializeTables(initializeTablesDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(bookingService, times(1)).initializeTables(anyInt());
    }

    @Test
    void testInitializeTables_AlreadyInitialized() {
        InitializeTablesDTO initializeTablesDTO = new InitializeTablesDTO();
        initializeTablesDTO.setNumberOfTables(BigDecimal.TEN);

        when(bookingService.isInitialized()).thenReturn(true);

        assertThrows(TablesAlreadyInitializedException.class, () -> {
            initializeTablesController.initializeTables(initializeTablesDTO);
        });

        verify(bookingService, never()).initializeTables(anyInt());
    }
}
