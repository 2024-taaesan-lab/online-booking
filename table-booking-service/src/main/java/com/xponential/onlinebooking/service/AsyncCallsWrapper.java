package com.xponential.onlinebooking.service;

import com.xponential.onlinebooking.model.Reservation;
import com.xponential.onlinebooking.model.TableModel;
import com.xponential.onlinebooking.repository.ReservationRepository;
import com.xponential.onlinebooking.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncCallsWrapper {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Async(value = "customTableExecutor")
    public void initializeTablesAsync(int numberOfTables){
        List<TableModel> tables = new ArrayList<>();
        for (int i = 0; i < numberOfTables; i++) {
            TableModel table = new TableModel();
            tables.add(table);
        }

        tableRepository.saveAll(tables);
    }

    @Async(value = "customTableExecutor")
    public void reserveTablesAsync(int requiredTables, List<TableModel> availableTables, Reservation reservation){
        reservationRepository.save(reservation);

        List<TableModel> reservedTables = new ArrayList<>();
        for (int i = 0; i < requiredTables; i++) {
            TableModel table = availableTables.get(i);
            table.setReserved(true);
            table.setReservationId(reservation.getReservationId());
            reservedTables.add(table);
        }
        tableRepository.saveAll(reservedTables);
    }

    @Async(value = "customTableExecutor")
    public void cancelReservationAsync(List<TableModel> freeTables, Reservation reservation){
        tableRepository.saveAll(freeTables);

        reservationRepository.delete(reservation);
    }
}
