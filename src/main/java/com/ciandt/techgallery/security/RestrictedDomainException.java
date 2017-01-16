package com.ciandt.techgallery.security;

public class RestrictedDomainException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RestrictedDomainException(String message) {
    super(message);
  }

}
