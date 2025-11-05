package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.enums.UserType;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.repository.ConsumerRepository;

@Service
public class UserService {

  @Autowired
  ConsumerRepository consumerRepository;

  public User saveUser(User user){
    return consumerRepository.save(user);
  }

  public User getUserById(UUID userId){
    return this.consumerRepository.findById(userId).orElse(null);
  }

  public boolean isMaint(User user){
    return user.getUsertype().equals(UserType.MAINT.toString());
  }

  public boolean isAdmin(User user){
    return user.getUsertype().equals(UserType.ZEPTO_APP_ADMIN.toString());
  }

  public User isValid(String email, String password){
    User user = consumerRepository.findByEmail(email);
    if(user != null && user.getEmail().equals(email) && user.getPassword().equals(password)){
      return user;
    }return null;
  }

  
  
}
