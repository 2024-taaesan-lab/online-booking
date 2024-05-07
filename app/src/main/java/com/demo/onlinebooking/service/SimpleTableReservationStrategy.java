package com.demo.onlinebooking.service;

import com.demo.onlinebooking.model.BookingIDNotFoundException;
import com.demo.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.demo.onlinebooking.model.CancelReservationResponse;
import com.demo.onlinebooking.model.InitializeTablesResponse;
import com.demo.onlinebooking.model.ReserveTableResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service component for simple table reservation strategy.
 */
@Component(value = "simpleTableReservationStrategy")
@Profile("dev")
public class SimpleTableReservationStrategy implements TableReservationStrategy {

    /** Number of seats per table */
    public static final int SEAT_PER_TABLE = 4;

    private Deque<UUID> availableTables; // Queue to store available tables
    private Deque<UUID> reservedTables; // Queue to store reserved tables
    private Map<UUID, Deque<UUID>> reservedTableMap; // Map to store reservation details

    /**
     * Constructs a SimpleTableReservationStrategy instance.
     */
    public SimpleTableReservationStrategy(){
        this.availableTables = new ArrayDeque<>();
        this.reservedTables = new ArrayDeque<>();
        this.reservedTableMap = new HashMap<>();
    }

    @Override
    public boolean isTableInitialized() {
        return false;
    }

    /**
     * Initializes the specified number of tables.
     * @param numberOfTables The number of tables to initialize.
     * @return Response containing the number of tables initialized.
     */
    @Override
    public InitializeTablesResponse initializeTables(int numberOfTables) {
        for (int i = 0; i < numberOfTables; i++) {
            availableTables.add(UUID.randomUUID()); // Each table initially has 4 seats
        }
        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }

    /**
     * Reserves tables for the specified number of customers.
     * @param numberOfCustomers The number of customers for whom tables need to be reserved.
     * @return Response containing booking details.
     * @throws NotEnoughTablesForAllCustomersException if there are not enough tables to accommodate all customers.
     */
    @Override
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);

        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        UUID bookingId = UUID.randomUUID();

        for(int i = requiredTables; i > 0; i--) {
            UUID table = availableTables.pollFirst(); // Remove first available table
            reservedTables.add(table); // Add reserved table
        }

        // Save bookingId for reserved tables
        reservedTableMap.put(bookingId, reservedTables);

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(bookingId.toString());
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }

    /**
     * Cancels a reservation identified by the given booking ID.
     * @param bookingId The booking ID of the reservation to cancel.
     * @return Response containing cancellation details.
     * @throws BookingIDNotFoundException if the booking ID is not found.
     */
    @Override
    public CancelReservationResponse cancelReservation(String bookingId) throws BookingIDNotFoundException {
        Deque<UUID> reservedTables = reservedTableMap.remove(bookingId); // Remove reservation details

        if (reservedTables == null) {
            throw new BookingIDNotFoundException();
        }

        availableTables.addAll(reservedTables); // Make reserved tables available again
        reservedTables.clear(); // Clear reserved tables

        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size())); // No. of canceled tables
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size())); // No. of available tables
        return response;
    }
}
