package com.sample.exceptions;

public class InterestTypeUnknownException extends RuntimeException {

  public InterestTypeUnknownException(final String message) {
    super(message);
  }
}
