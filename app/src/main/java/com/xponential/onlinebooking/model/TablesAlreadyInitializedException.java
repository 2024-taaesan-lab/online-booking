package com.xponential.onlinebooking.model;

/**
 * Custom exception class for handling cases where tables are already initialized.
 */
public class TablesAlreadyInitializedException extends RuntimeException {

    /**
     * Constructs a TablesAlreadyInitializedException with a default error message.
     */
    public TablesAlreadyInitializedException() {
        super("Tables already initialized");
    }
}
