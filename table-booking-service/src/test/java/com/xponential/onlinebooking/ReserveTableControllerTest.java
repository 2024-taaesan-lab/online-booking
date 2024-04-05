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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testReserveTables() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(4));

        when(bookingService.isInitialized()).thenReturn(true);
        when(bookingService.reserveTables(anyInt())).thenReturn(new ReserveTableResponse());

        ResponseEntity<ReserveTableResponse> responseEntity = reserveTableController.reserveTables(reserveTableDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(bookingService, times(1)).reserveTables(anyInt());
    }

    @Test
    void testReserveTables_NotInitialized() {
        ReserveTableDTO reserveTableDTO = new ReserveTableDTO();
        reserveTableDTO.setNumberOfCustomers(BigDecimal.valueOf(4));

        when(bookingService.isInitialized()).thenReturn(false);

        assertThrows(TablesNotInitializedException.class, () -> {
            reserveTableController.reserveTables(reserveTableDTO);
        });

        verify(bookingService, never()).reserveTables(anyInt());
    }
}

