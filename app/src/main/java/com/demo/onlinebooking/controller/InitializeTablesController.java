package com.demo.onlinebooking.controller;

import com.demo.onlinebooking.model.InitializeTablesDTO;
import com.demo.onlinebooking.service.TableReservationService;
import com.demo.onlinebooking.model.InitializeTablesResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for initializing tables.
 */
@RestController
@RequestMapping("/api/v1")
public class InitializeTablesController implements InitializeTablesApi {
    private static final Logger logger = LoggerFactory.getLogger(InitializeTablesController.class);

    private final TableReservationService tableReservationService;

    /**
     * Constructs an InitializeTablesController instance.
     * @param tableReservationService The table reservation service used for table initialization.
     */
    @Autowired
    public InitializeTablesController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }

    /**
     * Endpoint for initializing tables.
     * @param initializeTablesDTO The data containing the number of tables to initialize.
     * @return ResponseEntity containing the initialization response.
     */
    @Override
    @PostMapping("/initializeTables")
    public ResponseEntity<InitializeTablesResponse> initializeTables(@Valid @RequestBody InitializeTablesDTO initializeTablesDTO) {
        long start = System.currentTimeMillis();
        ResponseEntity<InitializeTablesResponse> resp = ResponseEntity.ok(tableReservationService.initializeTables(initializeTablesDTO.getNumberOfTables().intValue()));
        logger.info("The current Thread is : "+Thread.currentThread().getName());
        logger.info("Elapsed time: %s ms", (System.currentTimeMillis() - start));
        return resp;
    }

}
