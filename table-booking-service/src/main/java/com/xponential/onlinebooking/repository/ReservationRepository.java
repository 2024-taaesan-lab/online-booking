package com.xponential.onlinebooking.repository;

import com.xponential.onlinebooking.model.Reservation;
import com.xponential.onlinebooking.model.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Reservation entities.
 */
/*
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {


    @Query("SELECT u FROM Reservation u WHERE u.reservationId = :bookingId")
    List<Reservation> findReservationByReservationId(@Param("bookingId") String bookingId);

}
*/
