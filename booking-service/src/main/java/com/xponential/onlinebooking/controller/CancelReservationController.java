package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationDTO;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
        if (bookingService.getTables().isEmpty()) {
            throw new TablesNotInitializedException();
        }

        int freedTables = 0;

        for (Map.Entry<UUID, Integer> entry : bookingService.getTables().entrySet()) {
            if (entry.getKey().equals(cancelReservationDTO.getBookingId())) {
                freedTables += entry.getValue();
                bookingService.getTables().remove(entry.getKey());
            }
        }

        if (freedTables == 0) {
            throw new BookingIDNotFoundException();
        }

        CancelReservationResponse response = new CancelReservationResponse();
        response.setFreedTables(BigDecimal.valueOf(freedTables));
        response.setRemainingTables(BigDecimal.valueOf(bookingService.getTables().size()));
        return ResponseEntity.ok(response);
    }
}
