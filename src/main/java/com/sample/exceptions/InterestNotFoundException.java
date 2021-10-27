package com.sample.exceptions;

public class InterestNotFoundException extends RuntimeException {

    public InterestNotFoundException(final String message) {
        super(message);
    }
}
