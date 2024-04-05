package com.xponential.onlinebooking.model;

public class NotEnoughTablesForAllCustomersException extends RuntimeException {
    public NotEnoughTablesForAllCustomersException() {
        super("Not enough tables for all customers");
    }
}
