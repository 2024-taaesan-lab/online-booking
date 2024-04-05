package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CancelReservationController implements CancelReservationApi {

    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/cancelReservation")
    public ResponseEntity<CancelReservationResponse> cancelReservation(CancelReservationDTO cancelReservationDTO) {
        if (!bookingService.isInitialized()) {
            throw new TablesNotInitializedException();
        }
        return ResponseEntity.ok(bookingService.cancelReservation(cancelReservationDTO.getBookingId()));
    }
}
