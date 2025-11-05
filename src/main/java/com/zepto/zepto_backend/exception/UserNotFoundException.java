package com.zepto.zepto_backend.exception;

public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(String message){
    super(message);
  }
  
}
