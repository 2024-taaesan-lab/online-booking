package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import com.xponential.onlinebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReserveTableController implements ReserveTableApi {
    @Autowired
    private BookingService bookingService;
    @Override
    @PostMapping("/reserveTable")
    public ResponseEntity<ReserveTableResponse> reserveTables(ReserveTableDTO reserveTableDTO) {
        if (!bookingService.isInitialized()) {
            throw new TablesNotInitializedException();
        }
        return ResponseEntity.ok(bookingService.reserveTables(reserveTableDTO.getNumberOfCustomers().intValue()));
    }
}
