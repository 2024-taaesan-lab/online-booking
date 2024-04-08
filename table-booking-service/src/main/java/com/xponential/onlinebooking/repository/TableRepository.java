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
@Repository
public interface TableRepository extends JpaRepository<TableModel, UUID> {

    /**
     * Custom query method to find available tables (not reserved).
     * @return List of available tables.
     */
    @Query("SELECT u FROM TableModel u WHERE u.reserved = false")
    List<TableModel> findAvailableTables();

    /**
     * Custom query method to find tables reserved by a specific booking ID.
     * @param bookingId The booking ID to search for.
     * @return List of tables reserved by the given booking ID.
     */
    @Query("SELECT u FROM TableModel u WHERE u.reservationId = :bookingId")
    List<TableModel> findReservedTablesByBookingId(@Param("bookingId") String bookingId);

    /**
     * Custom query method to count reserved or unreserved tables.
     * @param reserved Boolean indicating whether to count reserved or unreserved tables.
     * @return Count of tables based on the reservation status.
     */
    @Query("SELECT COUNT(u) FROM TableModel u WHERE u.reserved = :reserved")
    long countReservedTables(@Param("reserved") boolean b);
}
