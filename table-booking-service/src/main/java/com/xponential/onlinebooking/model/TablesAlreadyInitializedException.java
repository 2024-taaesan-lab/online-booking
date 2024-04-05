package com.xponential.onlinebooking.model;

public class TablesAlreadyInitializedException extends RuntimeException {
    public TablesAlreadyInitializedException() {
        super("Tables already initialized");
    }
}
