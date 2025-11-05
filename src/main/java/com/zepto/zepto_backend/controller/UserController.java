package com.zepto.zepto_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.UserResponseBody;
import com.zepto.zepto_backend.dtos.UserSignInRequestBody;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.security.JWTUtil;
import com.zepto.zepto_backend.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  JWTUtil jwtUtil;
  
  @PostMapping("/signin")
  public ResponseEntity<String> signIn(@RequestBody UserSignInRequestBody userSignInRequestBody){
    String email = userSignInRequestBody.getEmail();
    String password = userSignInRequestBody.getPassword();
    User response = userService.isValid(email, password);
    if(response != null){
      String token = jwtUtil.generateToken(email, password);
      UserResponseBody userResponseBody = new UserResponseBody();
      userResponseBody.setUser(response);
      userResponseBody.setToken(token);
      return new ResponseEntity<>("Sign In SuccesFull = "+ token, HttpStatus.OK);
    }else{
      return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
  }

}
