package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.service.TableReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CancelReservationController implements CancelReservationApi {
    private final TableReservationService tableReservationService;

    @Autowired
    public CancelReservationController(@Qualifier("jpaTableReservationStrategy") TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }
    @Override
    @PostMapping("/cancelReservation")
    public ResponseEntity<CancelReservationResponse> cancelReservation(@Valid @RequestBody CancelReservationDTO cancelReservationDTO) {

        return ResponseEntity.ok(tableReservationService.cancelReservation(cancelReservationDTO.getBookingId()));
    }
}
