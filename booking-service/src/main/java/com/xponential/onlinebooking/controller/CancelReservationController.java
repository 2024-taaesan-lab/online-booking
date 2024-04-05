package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CancelReservationController implements CancelReservationApi {
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/cancelReservation")
    public ResponseEntity<CancelReservationResponse> cancelReservation(CancelReservationDTO cancelReservationDTO) {
        if (bookingService.getAvailableTables().isEmpty()) {
            throw new TablesNotInitializedException();
        }

        Deque<UUID> reservedTables = bookingService.getReservedTableMap().remove(cancelReservationDTO.getBookingId());

        if (reservedTables == null) {
            throw new BookingIDNotFoundException();
        }

        bookingService.getAvailableTables().addAll(reservedTables);
        bookingService.getReservedTables().clear();

        CancelReservationResponse response = new CancelReservationResponse();
        response.setRemainingTables(BigDecimal.valueOf(bookingService.getAvailableTables().size()));
        return ResponseEntity.ok(response);
    }
}
