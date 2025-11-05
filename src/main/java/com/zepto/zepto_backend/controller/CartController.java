package com.zepto.zepto_backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.CartRequestBody;
import com.zepto.zepto_backend.service.CartService;


@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

  @Autowired
  CartService cartService;

  @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequestBody cartRequestBody) {
        try {
            cartService.addToCart(cartRequestBody);
            return ResponseEntity.ok("Item added to cart successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to add item to cart: " + e.getMessage());
        }
    }

    @GetMapping("/review")
    public ResponseEntity<?> getCartItems(@RequestParam UUID userId){
        try {
            return ResponseEntity.ok(cartService.getCartItems(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to fetch items from cart: " + e.getMessage());
        }
    }
}
