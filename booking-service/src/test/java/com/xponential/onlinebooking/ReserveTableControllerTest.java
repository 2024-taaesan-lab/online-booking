package com.xponential.onlinebooking;

import com.xponential.onlinebooking.controller.ReserveTableController;
import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReserveTableControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ReserveTableController reserveTableController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testReserveTables_Success() {
        // Arrange
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(6));

        List<List<Integer>> tables = new ArrayList<>();
        tables.add(new ArrayList<>(Arrays.asList(1, 1, 1, 1))); // Fully occupied table
        tables.add(new ArrayList<>(Arrays.asList(1, 1))); // Empty table
        tables.add(new ArrayList<>()); // Empty table


        when(bookingService.getTables()).thenReturn(tables);

        // Act
        ResponseEntity<ReserveTableResponse> responseEntity = reserveTableController.reserveTables(reserveTableDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getBookedTables().intValue());
        assertEquals(1, responseEntity.getBody().getRemainingTables().intValue());

        // Verify that bookingService.getTables() is called once
//        verify(bookingService, times(1)).getTables();
    }

    @Test
    void testReserveTables_TablesNotInitialized() {
        // Arrange
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(6));

        when(bookingService.getTables()).thenReturn(new ArrayList<>()); // Empty list indicating tables not initialized

        // Act and Assert
        try {
            reserveTableController.reserveTables(reserveTableDTO);
        } catch (TablesNotInitializedException e) {
            // Expected exception
        }

        // Verify that bookingService.getTables() is called once
        verify(bookingService, times(1)).getTables();
    }

    // Add more test methods for other scenarios such as not enough tables for all customers
}

