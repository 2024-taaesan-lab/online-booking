package com.xponential.onlinebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;
@Entity
public class Reservation {
    @Id
    private UUID bookingId;

    @ManyToMany
    private List<Table> tables;

    // Add any other relevant fields, such as reservation time, customer information, etc.

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}