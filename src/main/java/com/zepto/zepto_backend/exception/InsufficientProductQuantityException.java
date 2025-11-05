package com.zepto.zepto_backend.exception;

public class InsufficientProductQuantityException extends RuntimeException{
  public InsufficientProductQuantityException(String message){
    super(message);
  }
}
