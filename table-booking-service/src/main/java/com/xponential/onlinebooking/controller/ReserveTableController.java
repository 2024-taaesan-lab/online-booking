package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ReserveTableController implements ReserveTableApi {

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/reserveTable")
    public ResponseEntity<ReserveTableResponse> reserveTables(ReserveTableDTO reserveTableDTO) {
        if (!bookingService.isInitialized()) {
            throw new TablesNotInitializedException();
        }

        int requiredTables = (int) Math.ceil((double) reserveTableDTO.getNumberOfCustomers().intValue() / 4);

        if (requiredTables > bookingService.getAvailableTables().size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        UUID bookingId = UUID.randomUUID();

        for(int i = requiredTables; i > 0; i--) {
            UUID table = bookingService.getAvailableTables().pollFirst();
            bookingService.getReservedTables().add(table);
        }

        bookingService.getReservedTableMap().put(bookingId, bookingService.getReservedTables());

        ReserveTableResponse reserveTableResponse = new ReserveTableResponse();
        reserveTableResponse.setBookingId(bookingId);
        reserveTableResponse.setBookedTables(BigDecimal.valueOf(bookingService.getReservedTables().size()));
        reserveTableResponse.setRemainingTables(BigDecimal.valueOf(bookingService.getAvailableTables().size()));

        return ResponseEntity.ok(reserveTableResponse);
    }
}
