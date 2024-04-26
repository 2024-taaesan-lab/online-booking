package com.demo.onlinebooking.service;

import com.demo.onlinebooking.model.BookingIDNotFoundException;
import com.demo.onlinebooking.model.CancelReservationResponse;
import com.demo.onlinebooking.model.InitializeTablesResponse;
import com.demo.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.demo.onlinebooking.model.ReserveTableResponse;
import com.demo.onlinebooking.model.TablesAlreadyInitializedException;
import com.demo.onlinebooking.model.TablesNotInitializedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service class for managing table reservations.
 */
@Service
public class TableReservationService {

    private TableReservationStrategy reservationStrategy;
    private boolean initialized = false;

    /**
     * Constructs a TableReservationService instance with the specified reservation strategy.
     * @param reservationStrategy The reservation strategy to be used.
     */
    public TableReservationService(@Qualifier("jpaTableReservationStrategy") TableReservationStrategy reservationStrategy) {
        this.reservationStrategy = reservationStrategy;
    }

    /**
     * Initializes the tables with the specified number.
     * @param numberOfTables The number of tables to initialize.
     * @return Response indicating the initialization status.
     * @throws TablesAlreadyInitializedException if tables are already initialized.
     */
    public InitializeTablesResponse initializeTables(int numberOfTables) {
        initialized = reservationStrategy.isTableInitialized();
        if (!initialized) {
            InitializeTablesResponse response = reservationStrategy.initializeTables(numberOfTables);
            initialized = true;
            return response;
        } else {
            throw new TablesAlreadyInitializedException();
        }
    }

    /**
     * Reserves tables for the specified number of customers.
     * @param numberOfCustomers The number of customers for whom tables need to be reserved.
     * @return Response containing booking details.
     * @throws NotEnoughTablesForAllCustomersException if there are not enough tables to accommodate all customers.
     * @throws TablesNotInitializedException if tables are not initialized.
     */
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        checkInitialized(); // Check if tables are initialized
        return reservationStrategy.reserveTables(numberOfCustomers);
    }

    /**
     * Cancels a reservation identified by the given booking ID.
     * @param bookingId The booking ID of the reservation to cancel.
     * @return Response containing cancellation details.
     * @throws BookingIDNotFoundException if the booking ID is not found.
     * @throws TablesNotInitializedException if tables are not initialized.
     */
    public CancelReservationResponse cancelReservation(String bookingId) throws BookingIDNotFoundException {
        checkInitialized(); // Check if tables are initialized
        return reservationStrategy.cancelReservation(bookingId);
    }

    /**
     * Checks if tables are initialized; throws exception if not.
     * @throws TablesNotInitializedException if tables are not initialized.
     */
    private void checkInitialized() {
        if (!initialized) {
            throw new TablesNotInitializedException();
        }
    }
}
