package com.xponential.onlinebooking.service;
import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SimpleTableReservationStrategy implements TableReservationStrategy {

    public static final int SEAT_PER_TABLE = 4;

    private Deque<UUID> availableTables;
    private Deque<UUID> reservedTables;
    private Map<UUID, Deque> reservedTableMap;

    public SimpleTableReservationStrategy(){
        this.availableTables = new ArrayDeque<>();
        this.reservedTables = new ArrayDeque<>();
        this.reservedTableMap = new HashMap<>();
    }

    @Override
    public InitializeTablesResponse initializeTables(int numberOfTables) {
        for (int i = 0; i < numberOfTables; i++) {
            availableTables.add(UUID.randomUUID()); // Each table initially has 4 seats
        }
        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }

    @Override
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);

        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        UUID bookingId = UUID.randomUUID();

        for(int i = requiredTables; i > 0; i--) {
            UUID table = availableTables.pollFirst();
            reservedTables.add(table);
        }

        // Save bookingId for reserved tables
        reservedTableMap.put(bookingId, reservedTables);

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(bookingId);
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }

    @Override
    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        Deque<UUID> reservedTables = reservedTableMap.remove(bookingId);

        if (reservedTables == null) {
            throw new BookingIDNotFoundException();
        }

        availableTables.addAll(reservedTables);
        reservedTables.clear();

        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }
}
