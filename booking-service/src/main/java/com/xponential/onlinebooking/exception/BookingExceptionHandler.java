package com.xponential.onlinebooking.exception;

import com.xponential.onlinebooking.model.TablesAlreadyInitializedException;
import com.xponential.onlinebooking.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
@ControllerAdvice
public class BookingExceptionHandler {

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setStatus(HttpStatus.CONFLICT.value());
//        errorResponse.setMessage(ConstraintViolationParser.parse(ex));
//
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TablesAlreadyInitializedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> createResponse(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
