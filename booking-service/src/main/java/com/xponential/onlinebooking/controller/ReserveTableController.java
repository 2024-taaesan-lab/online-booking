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
        if (bookingService.getTables().isEmpty()) {
            throw new TablesNotInitializedException();
        }


        int requiredTables = (int) Math.ceil((double) reserveTableDTO.getNumberOfCustomers().intValue() / 4);

        if (requiredTables > bookingService.getTables().keySet().size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        int reservedTables = 0;
        UUID bookingId = UUID.randomUUID();

        for (Map.Entry<UUID, Integer> entry : bookingService.getTables().entrySet()) {
            if (requiredTables == 0) {
                break;
            }
            entry.setValue(entry.getValue() - 1);
            reservedTables++;
            if (entry.getValue() == 0) {
                bookingService.getTables().remove(entry.getKey());
            }
            requiredTables--;
        }

        int remainingTables = bookingService.getTables().size() - reservedTables;
        ReserveTableResponse reserveTableResponse = new ReserveTableResponse();
        reserveTableResponse.setBookingId(bookingId);
        reserveTableResponse.setBookedTables(BigDecimal.valueOf(reservedTables));
        reserveTableResponse.setRemainingTables(BigDecimal.valueOf(remainingTables));

//        return "Reservation successful. Booked tables: " + bookedTables + ". Remaining tables: " + remainingTables;
        return ResponseEntity.ok(reserveTableResponse);
    }
}
