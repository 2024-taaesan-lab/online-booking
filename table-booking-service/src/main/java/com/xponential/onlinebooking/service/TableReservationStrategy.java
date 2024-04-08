package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;

import java.util.UUID;

/**
 * Interface defining the contract for table reservation strategies.
 * Different implementations of this interface can provide various strategies for initializing tables, reserving tables,
 * and canceling reservations.
 */
public interface TableReservationStrategy {

    /**
     * Initializes tables based on the specified number.
     * @param numberOfTables The number of tables to initialize.
     * @return Response indicating the status of table initialization.
     */
    InitializeTablesResponse initializeTables(int numberOfTables);

    /**
     * Reserves tables for the specified number of customers.
     * @param numberOfCustomers The number of customers for whom tables need to be reserved.
     * @return Response containing booking details.
     * @throws NotEnoughTablesForAllCustomersException if there are not enough tables to accommodate all customers.
     */
    ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException;

    /**
     * Cancels a reservation identified by the given booking ID.
     * @param bookingId The booking ID of the reservation to cancel.
     * @return Response containing cancellation details.
     * @throws BookingIDNotFoundException if the booking ID is not found.
     */
    CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException;
}
