package com.xponential.onlinebooking.model;

/**
 * Custom exception class for handling cases where there are not enough tables for all customers.
 */
public class NotEnoughTablesForAllCustomersException extends RuntimeException {

    /**
     * Constructs a NotEnoughTablesForAllCustomersException with a default error message.
     */
    public NotEnoughTablesForAllCustomersException() {
        super("Not enough tables for all customers");
    }
}
