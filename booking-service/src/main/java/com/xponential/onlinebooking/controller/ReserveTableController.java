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
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReserveTableController implements ReserveTableApi {

    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/reserveTable")
    public ResponseEntity<ReserveTableResponse> reserveTables(ReserveTableDTO reserveTableDTO) {
        if (bookingService.getTables().isEmpty()) {
            throw new TablesNotInitializedException();
        }

        int remainingCustomers = reserveTableDTO.getNumberOfCustomers().intValue();
        int bookedTables = 0;

        for (List<Integer> table : bookingService.getTables()) {
            int remainingSeats = 4 - table.size();
            if (remainingSeats >= remainingCustomers) {
                for (int i = 0; i < remainingCustomers; i++) {
                    table.add(1); // Add customers to the table
                }
                bookedTables++;
                break;
            } else if (remainingSeats > 0) {
                for (int i = 0; i < remainingSeats; i++) {
                    table.add(1); // Add customers to the table
                }
                remainingCustomers -= remainingSeats;
                bookedTables++;
            }
        }

        if (bookedTables == 0) {
            throw new NotEnoughTablesForAllCustomersException();
        }
        int remainingTables = bookingService.getTables().size() - bookedTables;
        ReserveTableResponse reserveTableResponse = new ReserveTableResponse();
        reserveTableResponse.setBookedTables(BigDecimal.valueOf(bookedTables));
        reserveTableResponse.setRemainingTables(BigDecimal.valueOf(remainingTables));

//        return "Reservation successful. Booked tables: " + bookedTables + ". Remaining tables: " + remainingTables;
        return ResponseEntity.ok(reserveTableResponse);
    }
}
