package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TableReservationService {

    private TableReservationStrategy reservationStrategy;
    private boolean initialized = false;

    public TableReservationService(@Qualifier("jpaTableReservationStrategy") TableReservationStrategy reservationStrategy) {
        this.reservationStrategy = reservationStrategy;
    }

    public InitializeTablesResponse initializeTables(int numberOfTables) {
        if (!initialized) {
            InitializeTablesResponse response = reservationStrategy.initializeTables(numberOfTables);
            initialized = true;
            return response;
        } else {
            throw new TablesAlreadyInitializedException();
        }
    }

    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        checkInitialized();
        return reservationStrategy.reserveTables(numberOfCustomers);
    }

    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        checkInitialized();
        return reservationStrategy.cancelReservation(bookingId);
    }

    private void checkInitialized() {
        if (!initialized) {
            throw new TablesNotInitializedException();
        }
    }
}
