package com.xponential.onlinebooking.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private List<List<Integer>> tables;
    public List<List<Integer>> getTables() {
        return tables;
    }

    public void setTables(List<List<Integer>> tables) {
        this.tables = tables;
    }

    BookingService(){
        tables = new ArrayList<>();
    }
}
