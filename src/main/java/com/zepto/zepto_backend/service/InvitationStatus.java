package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.enums.UserStatus;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.model.User;

@Service
public class InvitationStatus {

  @Autowired
  UserService userService;

  public void updateAcceptStatus(UUID userId){
    User user = userService.getUserById(userId);
    if(user == null){
      throw new UserNotFoundException(String.format("User With Id %s Not Found", userId.toString()));
    }
    user.setStatus(UserStatus.ACTIVE.toString());

    userService.saveUser(user);
  }

  public void updateRejectStatus(UUID userId){
    User user = userService.getUserById(userId);
     if(user == null){
      throw new UserNotFoundException(String.format("User With Id %s Not Found", userId.toString()));
    }
    user.setStatus(UserStatus.REJECTED.toString());
    userService.saveUser(user);

  }
}
