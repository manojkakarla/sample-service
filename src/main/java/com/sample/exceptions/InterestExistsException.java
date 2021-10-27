package com.sample.exceptions;

public class InterestExistsException extends RuntimeException {

    public InterestExistsException(final String message) {
        super(message);
    }
}
