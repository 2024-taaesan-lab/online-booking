package com.xponential.onlinebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    private UUID bookingId;

    @OneToMany
    @JoinColumn(name = "reservation_id")
    private List<TableModel> tables;

    // Add any other relevant fields, such as reservation time, customer information, etc.

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public List<TableModel> getTable() {
        return tables;
    }

    public void setTables(List<TableModel> tables) {
        this.tables = tables;
    }
}