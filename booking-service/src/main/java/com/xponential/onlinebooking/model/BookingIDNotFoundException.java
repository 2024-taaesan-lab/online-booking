package com.xponential.onlinebooking.model;

public class BookingIDNotFoundException extends RuntimeException {
    public BookingIDNotFoundException() {
        super("Booking ID not found.");
    }
}
