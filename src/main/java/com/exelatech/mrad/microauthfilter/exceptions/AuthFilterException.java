package com.exelatech.mrad.authfilter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * FilterException
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AuthFilterException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public AuthFilterException() {
  }

  public AuthFilterException(String message) {
    super(message);
  }

  public AuthFilterException(Throwable cause) {
    super(cause);
  }

  public AuthFilterException(String message, Throwable cause) {
    super(message, cause);
  }

  
}