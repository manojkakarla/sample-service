package com.sample.resource;

import com.sample.exceptions.InterestExistsException;
import com.sample.exceptions.InterestNotFoundException;
import com.sample.exceptions.InterestTypeUnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(InterestNotFoundException.class)
    public ResponseEntity<Void> handleInterestNotFoundException() {
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInterestExistsException(InterestExistsException ex) {
        return ResponseEntity.status(CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInterestTypeUnknownException(InterestTypeUnknownException ex) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
