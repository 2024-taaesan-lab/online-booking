package com.xponential.onlinebooking.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "table_model")
public class TableModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "reserved", nullable = false)
    private boolean reserved;

    @ManyToOne()
    @JoinColumn(name ="reservation_id", nullable = true)
    private Reservation reservation;

    // Add any other relevant fields

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}