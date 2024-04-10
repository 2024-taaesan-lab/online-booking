package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.Reservation;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TableModel;
import com.xponential.onlinebooking.repository.ReservationRepository;
import com.xponential.onlinebooking.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for handling table reservations using a database-backed strategy.
 */
@Component(value = "jpaTableReservationStrategy")
public class DatabaseTableReservationStrategy implements TableReservationStrategy {

    /** Number of seats per table */
    public static final int SEAT_PER_TABLE = 4;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AsyncCallsWrapper asyncCallWrapper;

    /**
     * Initializes the specified number of tables.
     * @param numberOfTables The number of tables to initialize.
     * @return Response containing the number of tables initialized.
     */
    @Override
    public InitializeTablesResponse initializeTables(int numberOfTables) {

        /* Sync call */
//        List<TableModel> tables = new ArrayList<>();
//        for (int i = 0; i < numberOfTables; i++) {
//            TableModel table = new TableModel();
//            tables.add(table);
//        }
//        tableRepository.saveAll(tables);

        //Async call
        asyncCallWrapper.initializeTablesAsync(numberOfTables);


        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(numberOfTables));
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
        List<TableModel> availableTables = tableRepository.findAvailableTables();
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);

        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }
        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());

        /* Sync call */
//        reservationRepository.save(reservation);
//        List<TableModel> reservedTables = new ArrayList<>();
//        for (int i = 0; i < requiredTables; i++) {
//            TableModel table = availableTables.get(i);
//            table.setReserved(true);
//            table.setReservationId(reservation.getReservationId());
//            reservedTables.add(table);
//        }
//        tableRepository.saveAll(reservedTables);

        //Async Call
        asyncCallWrapper.reserveTablesAsync(requiredTables, availableTables, reservation);

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(UUID.fromString(reservation.getReservationId()));
        response.setBookedTables(BigDecimal.valueOf(requiredTables));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size() - requiredTables));
        return response;
    }

    /**
     * Cancels a reservation identified by the given booking ID.
     * @param bookingId The booking ID of the reservation to cancel.
     * @return Response containing cancellation details.
     * @throws BookingIDNotFoundException if the booking ID is not found.
     */
    @Override
    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        List<Reservation> reservation = reservationRepository.findReservationByReservationId(bookingId.toString());

        if (reservation == null || reservation.isEmpty()) {
            throw new BookingIDNotFoundException();
        }

        List<TableModel> reservedTables = tableRepository.findReservedTablesByBookingId(bookingId.toString());

        List<TableModel> freeTables = new ArrayList<>();
        for (TableModel table : reservedTables) {
            table.setReserved(false);
            table.setReservationId(null);
            freeTables.add(table);
        }

        /* Sync call */
//        tableRepository.saveAll(freeTables);
//        reservationRepository.delete(reservation.get(0));

        //Async Call
        asyncCallWrapper.cancelReservationAsync(freeTables, reservation.get(0));


        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(tableRepository.countReservedTables(false)));
        return response;
    }
}
