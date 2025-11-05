package com.zepto.zepto_backend.exception;

public class UnauthorizedException extends RuntimeException{
  public UnauthorizedException(String message){
    super(message);
  }
}
