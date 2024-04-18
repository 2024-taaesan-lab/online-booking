package com.xponential.onlinebooking.repository;

import com.xponential.onlinebooking.model.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing TableModel entities.
 */
/*
@Repository
public interface TableRepository extends JpaRepository<TableModel, UUID> {


    @Query("SELECT u FROM TableModel u WHERE u.reserved = false")
    List<TableModel> findAvailableTables();


    @Query("SELECT u FROM TableModel u WHERE u.reservationId = :bookingId")
    List<TableModel> findReservedTablesByBookingId(@Param("bookingId") String bookingId);

    @Query("SELECT COUNT(u) FROM TableModel u WHERE u.reserved = :reserved")
    long countReservedTables(@Param("reserved") boolean b);
}

 */
