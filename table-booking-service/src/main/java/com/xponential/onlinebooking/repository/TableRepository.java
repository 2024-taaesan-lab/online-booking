package com.xponential.onlinebooking.repository;

import com.xponential.onlinebooking.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<Table, UUID> {
    List<Table> findAvailableTables();

    List<Table> findReservedTablesByBookingId(UUID bookingId);

    long countReservedTables(boolean b);
    // Add custom query methods if needed
}
