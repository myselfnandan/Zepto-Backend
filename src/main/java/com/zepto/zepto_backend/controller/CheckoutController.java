package com.zepto.zepto_backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.CheckoutRequestBody;
import com.zepto.zepto_backend.service.CheckoutService;

@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {

  @Autowired
  CheckoutService checkoutService;

  @PostMapping("/order")
  public ResponseEntity<String> checkoutPage(@RequestBody CheckoutRequestBody checkoutRequestBody, @RequestParam UUID userId){
    try{
      return ResponseEntity.ok(checkoutService.placeOrder(checkoutRequestBody, userId));
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to place order :"+ e.getMessage());
    }
  }
}
