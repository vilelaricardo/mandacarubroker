package com.mandacarubroker.controller.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ResourceExceptionHandler {

    private final HttpServletRequest http;

    public ResourceExceptionHandler(HttpServletRequest http) {
        this.http = http;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException exception){
        StandardError error = new StandardError(Instant.now(),HttpStatus.NOT_FOUND.value(),"Entity Not Found",exception.getMessage(),http.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> validationException(ConstraintViolationException exc){
        StandardError error = new StandardError(Instant.now(),HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Exception",exc.getMessage(),http.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
}
