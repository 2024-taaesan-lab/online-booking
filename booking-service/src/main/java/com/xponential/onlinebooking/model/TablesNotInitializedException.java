package com.xponential.onlinebooking.model;

public class TablesNotInitializedException extends RuntimeException {
    public TablesNotInitializedException() {
        super("Tables not initialized");
    }
}
