package com.xponential.onlinebooking.model;

/**
 * Custom exception class for handling cases where a booking ID is not found.
 */
public class BookingIDNotFoundException extends RuntimeException {

    /**
     * Constructs a BookingIDNotFoundException with a default error message.
     */
    public BookingIDNotFoundException() {
        super("Booking ID not found.");
    }
}
