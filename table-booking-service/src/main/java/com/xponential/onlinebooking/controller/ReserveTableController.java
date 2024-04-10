package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.ReserveTableDTO;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.service.TableReservationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling table reservation requests.
 */
@RestController
@RequestMapping("/api/v1")
public class ReserveTableController implements ReserveTableApi {

    private static final Logger logger = LoggerFactory.getLogger(ReserveTableController.class);
    private final TableReservationService tableReservationService;

    /**
     * Constructs a ReserveTableController instance.
     * @param tableReservationService The table reservation service used for handling reservation requests.
     */
    @Autowired
    public ReserveTableController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }

    /**
     * Endpoint for reserving tables.
     * @param reserveTableDTO The data containing the number of customers to reserve tables for.
     * @return ResponseEntity containing the reservation response.
     */
    @Override
    @PostMapping("/reserveTable")
    public ResponseEntity<ReserveTableResponse> reserveTables(@Valid @RequestBody ReserveTableDTO reserveTableDTO) {
        long start = System.currentTimeMillis();
        ResponseEntity<ReserveTableResponse> resp = ResponseEntity.ok(tableReservationService.reserveTables(reserveTableDTO.getNumberOfCustomers().intValue()));
        logger.info("The current Thread is : "+Thread.currentThread().getName());
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        return resp;
    }
}
