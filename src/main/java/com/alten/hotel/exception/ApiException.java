package com.alten.hotel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

  protected final HttpStatus status;
  private String errorMessage;

  public ApiException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public ApiException(HttpStatus status, String message, String errorMessage) {
    super(message);
    this.status = status;
    this.errorMessage = errorMessage;
  }

  public ApiException(HttpStatus status, Throwable throwable) {
    super(throwable);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
