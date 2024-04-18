package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.CancelReservationResponse;
import com.xponential.onlinebooking.model.InitializeTablesResponse;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.Reservation;
import com.xponential.onlinebooking.model.ReserveTableResponse;
import com.xponential.onlinebooking.model.TableModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for handling table reservations using a database-backed strategy.
 */
@Component(value = "jpaTableReservationStrategy")
public class DatabaseTableReservationStrategy implements TableReservationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTableReservationStrategy.class);

    /** Number of seats per table */
    public static final int SEAT_PER_TABLE = 4;

    @Autowired
    private EntityManager entityManager;

    /**
     * Initializes the specified number of tables.
     * @param numberOfTables The number of tables to initialize.
     * @return Response containing the number of tables initialized.
     */
    @Override
    @Transactional
    public InitializeTablesResponse initializeTables(int numberOfTables) {

        logger.info("entityManager: "+entityManager);

        for (int i = 0; i < numberOfTables; i++) {
            TableModel table = new TableModel();
            table.setTitle("T"+(i+1));
            entityManager.persist(table);
        }

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
    @Transactional
    public ReserveTableResponse reserveTables(int numberOfCustomers) throws NotEnoughTablesForAllCustomersException {

        logger.info("Counting table");
        TypedQuery<TableModel> query = entityManager.createQuery("SELECT t FROM TableModel t WHERE t.reservation IS NULL", TableModel.class);

        List<TableModel> availableTables = query.getResultList();
        logger.info("availableTables: "+availableTables.size());
        int requiredTables = (int) Math.ceil((double) numberOfCustomers / SEAT_PER_TABLE);
        logger.info("requiredTables: "+requiredTables);
        if (requiredTables > availableTables.size()) {
            throw new NotEnoughTablesForAllCustomersException();
        }

        Reservation reservation = new Reservation();
        reservation.setBookingId(UUID.randomUUID().toString());

        for (int i = 0; i < requiredTables; i++) {
            TableModel table = availableTables.get(i);
            reservation.add(table);
        }

        entityManager.persist(reservation);


        //Async Call
        //asyncCallWrapper.reserveTablesAsync(requiredTables, availableTables, reservation);

        ReserveTableResponse response = new ReserveTableResponse();
        response.setBookingId(UUID.fromString(reservation.getBookingId()));
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
    @Transactional
    public CancelReservationResponse cancelReservation(UUID bookingId) throws BookingIDNotFoundException {
        logger.info("---");
        logger.info("Finding reservation by bookingId: "+ bookingId.toString());
        TypedQuery<Reservation> query = entityManager.createQuery("""
                SELECT l FROM Reservation l
                JOIN FETCH l.tables
                WHERE l.bookingId = :id
                """, Reservation.class);
        query.setParameter("id", bookingId.toString());
        List<Reservation> reservationList = query.getResultList();

        if (reservationList == null || reservationList.isEmpty()){
            throw new BookingIDNotFoundException();
        }
        logger.info("Reservation Size: "+reservationList.size());

        logger.info("Break relationship with tables");
        Reservation reservation = reservationList.get(0);
        List<TableModel> tables = reservationList.get(0).getTables();
        for (TableModel item: tables){
            item.setReservation(null);
            entityManager.persist(item);
        }
        logger.info("Removing reservation: "+ reservation.getId());
        entityManager.remove(reservation);
        logger.info("Done!!!");

        TypedQuery<TableModel> query2 = entityManager.createQuery("SELECT t FROM TableModel t WHERE t.reservation IS NULL", TableModel.class);
        TypedQuery<TableModel> query3 = entityManager.createQuery("SELECT t FROM TableModel t WHERE t.reservation IS NOT NULL", TableModel.class);
        List<TableModel> availableTables = query2.getResultList();
        List<TableModel> reservedTables = query3.getResultList();


        CancelReservationResponse response = new CancelReservationResponse();
        response.setBookedTables(BigDecimal.valueOf(reservedTables.size()));
        response.setRemainingTables(BigDecimal.valueOf(availableTables.size()));
        return response;


    }
}
