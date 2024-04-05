package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.ReserveTableResponse;

import java.util.UUID;

public interface TableReservationStrategy {
    InitializeTablesResponse initializeTables(int numberOfTables);

    ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException;

    CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException;
}

