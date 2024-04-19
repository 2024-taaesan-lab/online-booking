package com.xponential.onlinebooking.model;

/**
 * Custom exception class for handling cases where tables are not initialized.
 */
public class TablesNotInitializedException extends RuntimeException {

    /**
     * Constructs a TablesNotInitializedException with a default error message.
     */
    public TablesNotInitializedException() {
        super("Tables not initialized");
    }
}
