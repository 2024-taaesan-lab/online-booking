package com.xponential.onlinebooking.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
