package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.model.User;

@Service
public class UserValidationService {

  @Autowired
  UserService userService;

  public void userValidation(UUID userId){
    User user = userService.getUserById(userId);
    if(user == null){
      throw new UserNotFoundException(String.format("User With ID %s Dosen't Exist", userId.toString()));
    }

    if(!userService.isMaint(user) && !userService.isAdmin(user)){
      throw new UnauthorizedException(String.format("User With Id %s Cannot Perform This Operation", userId.toString()));
    }
  }
}
