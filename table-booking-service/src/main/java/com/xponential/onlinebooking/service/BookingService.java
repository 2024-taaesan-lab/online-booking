package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingService {

    public static final int SEAT_PER_TABLE = 4;

    private Deque<UUID> availableTables;
    private Deque<UUID> reservedTables;
    private Map<UUID, Deque> reservedTableMap;
    private boolean initialized = false;

    public Deque<UUID> getAvailableTables() {
        return availableTables;
    }

    public Deque<UUID> getReservedTables() {
        return reservedTables;
    }

    public Map<UUID, Deque> getReservedTableMap() {
        return reservedTableMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public BookingService(){
        this.availableTables = new ArrayDeque<>();
        this.reservedTables = new ArrayDeque<>();
        this.reservedTableMap = new HashMap<>();
    }


    public InitializeTablesResponse initializeTables(int numberOfTables) {

        for (int i = 0; i < numberOfTables; i++) {
            availableTables.add(UUID.randomUUID()); // Each table initially has 4 seats
        }
        initialized = true;
        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(availableTables.size()));
        return response;
    }

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

    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException{
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
