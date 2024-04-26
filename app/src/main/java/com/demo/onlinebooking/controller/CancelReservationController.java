package com.demo.onlinebooking.controller;

import com.demo.onlinebooking.model.CancelReservationDTO;
import com.demo.onlinebooking.model.CancelReservationResponse;
import com.demo.onlinebooking.service.TableReservationService;
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
 * Controller class for handling cancellation of reservations.
 */
@RestController
@RequestMapping("/api/v1")
public class CancelReservationController implements CancelReservationApi {

    private static final Logger logger = LoggerFactory.getLogger(CancelReservationController.class);
    private final TableReservationService tableReservationService;

    /**
     * Constructs a CancelReservationController instance.
     * @param tableReservationService The table reservation service to use for cancellation.
     */
    @Autowired
    public CancelReservationController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }

    /**
     * Endpoint for cancelling a reservation.
     * @param cancelReservationDTO The cancellation request data.
     * @return ResponseEntity containing the cancellation response.
     */
    @Override
    @PostMapping("/cancelReservation")
    public ResponseEntity<CancelReservationResponse> cancelReservation(@Valid @RequestBody CancelReservationDTO cancelReservationDTO) {

        long start = System.currentTimeMillis();
        ResponseEntity<CancelReservationResponse> resp = ResponseEntity.ok(tableReservationService.cancelReservation(cancelReservationDTO.getBookingId()));
        logger.info("The current Thread is : "+Thread.currentThread().getName());
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        return resp;
    }
}
