package com.xponential.onlinebooking.exception;

import com.xponential.onlinebooking.model.BookingIDNotFoundException;
import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.model.ErrorResponse;
import com.xponential.onlinebooking.model.TablesNotInitializedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for booking-related exceptions.
 */
@ControllerAdvice
public class BookingExceptionHandler {

    /**
     * Handles TablesNotInitializedException and returns an appropriate error response.
     * @param ex The TablesNotInitializedException instance.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TablesNotInitializedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Handles TablesAlreadyInitializedException and returns an appropriate error response.
     * @param ex The TablesAlreadyInitializedException instance.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TablesAlreadyInitializedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles NotEnoughTablesForAllCustomersException and returns an appropriate error response.
     * @param ex The NotEnoughTablesForAllCustomersException instance.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotEnoughTablesForAllCustomersException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BookingIDNotFoundException and returns an appropriate error response.
     * @param ex The BookingIDNotFoundException instance.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BookingIDNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles any other runtime exception and returns a generic error response.
     * @param ex The runtime exception.
     * @return ResponseEntity containing the error response.
     */
    private ResponseEntity<ErrorResponse> createResponse(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
