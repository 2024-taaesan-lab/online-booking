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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component(value = "jpaTableReservationStrategy")
public class JpaTableReservationStrategy implements TableReservationStrategy {

    public static final int SEAT_PER_TABLE = 4;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public InitializeTablesResponse initializeTables(int numberOfTables) {
        for (int i = 0; i < numberOfTables; i++) {
            TableModel table = new TableModel();
            table.setTableKey(UUID.randomUUID().toString());
            tableRepository.save(table);
        }
        InitializeTablesResponse response = new InitializeTablesResponse();
        response.setNumberOfTables(BigDecimal.valueOf(numberOfTables));
        return response;
    }

    @Override
    @Transactional
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {
        List<TableModel> availableTables = tableRepository.findAvailableTables();
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);

        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());
        reservationRepository.save(reservation);

        for (int i = 0; i < requiredTables; i++) {
            TableModel table = availableTables.get(i);
            table.setReserved(true);
            table.setReservationId(reservation.getReservationId());
            tableRepository.save(table);
        }

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(UUID.fromString(reservation.getReservationId()));
        response.setBookedTables(BigDecimal.valueOf(requiredTables));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size() - requiredTables));
        return response;
    }

    @Override
    @Transactional
    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        List<Reservation> reservation = reservationRepository.findReservationByReservationId(bookingId.toString());

        if (reservation == null || reservation.size() == 0) {
            throw new BookingIDNotFoundException();
        }

        List<TableModel> reservedTables = tableRepository.findReservedTablesByBookingId(bookingId.toString());

        for (TableModel table : reservedTables) {
            table.setReserved(false);
            table.setReservationId(null);
            tableRepository.save(table);
        }

        reservationRepository.delete(reservation.get(0));

        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(tableRepository.countReservedTables(false)));
        return response;
    }
}