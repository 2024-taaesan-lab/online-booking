package com.xponential.onlinebooking.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingService {

    final Map<UUID, Integer> tables = new HashMap<>();

    public Map<UUID, Integer> getTables() {
        return tables;
    }
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


}
