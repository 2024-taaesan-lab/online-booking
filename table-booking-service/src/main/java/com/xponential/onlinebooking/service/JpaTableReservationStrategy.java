package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.Table;
import com.xponential.onlinebooking.repository.TableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class JpaTableReservationStrategy implements TableReservationStrategy {

    public static final int SEAT_PER_TABLE = 4;

    @Autowired
    private TableRepository tableRepository;

    @Override
    public InitializeTablesResponse initializeTables(int numberOfTables) {
        for (int i = 0; i < numberOfTables; i++) {
            Table table = new Table();
            table.setId(UUID.randomUUID());
            tableRepository.save(table);
        }
        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(numberOfTables));
        return response;
    }

    @Override
    @Transactional
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        List<Table> availableTables = tableRepository.findAvailableTables();
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);

        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        List<UUID> reservedTableIds = new ArrayList<>();
        for (int i = 0; i < requiredTables; i++) {
            Table table = availableTables.get(i);
            reservedTableIds.add(table.getId());
            table.setReserved(true);
            tableRepository.save(table);
        }

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(UUID.randomUUID());
        response.setBookedTables(BigDecimal.valueOf(requiredTables));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size() - requiredTables));
        return response;
    }

    @Override
    @Transactional
    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        List<Table> reservedTables = tableRepository.findReservedTablesByBookingId(bookingId);

        if (reservedTables.isEmpty()) {
            throw new BookingIDNotFoundException();
        }

        for (Table table : reservedTables) {
            table.setReserved(false);
            tableRepository.save(table);
        }

        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(tableRepository.countReservedTables(false)));
        return response;
    }
}
