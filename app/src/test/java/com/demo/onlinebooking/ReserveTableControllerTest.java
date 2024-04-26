package com.demo.onlinebooking;

import com.demo.onlinebooking.controller.ReserveTableController;
import com.demo.onlinebooking.model.ReserveTableDTO;
import com.demo.onlinebooking.service.TableReservationService;
import com.demo.onlinebooking.model.ReserveTableResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReserveTableControllerTest {

    @Mock
    private TableReservationService tableReservationService;

    @InjectMocks
    private ReserveTableController reserveTableController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void reserveTables_ValidRequest_Success() throws Exception {
        // Mocking behavior of TableReservationService
        ReserveTableDTO request = new ReserveTableDTO();
        request.setNumberOfCustomers(BigDecimal.valueOf(4)); // Example number of customers
        ReserveTableResponse expectedResponse = new ReserveTableResponse();
        // Assuming expected response setup here

        when(tableReservationService.reserveTables(anyInt())).thenReturn(expectedResponse);

        // Invoking controller method
        ResponseEntity<ReserveTableResponse> responseEntity = reserveTableController.reserveTables(request);

        // Verifying response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}

