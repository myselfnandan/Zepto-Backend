package com.zepto.zepto_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.ConsumerRequestBody;
import com.zepto.zepto_backend.service.ConsumerService;

@RestController
@RequestMapping("/api/v1/consumer")
public class ConsumerController {
  
  @Autowired
  ConsumerService consumerService;

  @PostMapping("/register")
  public String registerUser(@RequestBody ConsumerRequestBody consumerRequestBody){
    consumerService.createCustomer(consumerRequestBody);
    return "User Created Succesfully";
  }
  
}
