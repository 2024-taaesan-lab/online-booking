package com.xponential.onlinebooking.repository;

import com.xponential.onlinebooking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    // You can add custom query methods if needed
}