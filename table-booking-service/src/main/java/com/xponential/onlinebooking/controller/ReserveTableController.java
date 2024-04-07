package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
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
public class ReserveTableController implements ReserveTableApi {
    private final TableReservationService tableReservationService;

    @Autowired
    public ReserveTableController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }
    @Override
    @PostMapping("/reserveTable")
    public ResponseEntity<ReserveTableResponse> reserveTables(@Valid @RequestBody ReserveTableDTO reserveTableDTO) {
        return ResponseEntity.ok(tableReservationService.reserveTables(reserveTableDTO.getNumberOfCustomers().intValue()));
    }
}
