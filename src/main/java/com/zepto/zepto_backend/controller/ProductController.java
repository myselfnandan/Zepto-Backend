package com.zepto.zepto_backend.controller;

import java.util.List;
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

import com.zepto.zepto_backend.dtos.OrderListResponseBody;
import com.zepto.zepto_backend.dtos.ProductRequestBody;
import com.zepto.zepto_backend.dtos.ProductResponseBody;
import com.zepto.zepto_backend.exception.RecordDosenotExistException;
import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.service.ProductService;

@RestController
@RequestMapping("/api/v2/product")
public class ProductController {
  
  @Autowired
  ProductService productService;

  @PostMapping("/register")
  public ResponseEntity<String> createProduct(@RequestBody ProductRequestBody productRequestBody, @RequestParam UUID userId){
    try{
      productService.createProduct(productRequestBody, userId);
      return new ResponseEntity<>("Product Registration Completed",HttpStatus.ACCEPTED);
    }catch(UserNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }catch(UnauthorizedException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }catch(Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchProduct(@RequestParam String name, @RequestParam UUID userId) {
    try {
        List<ProductResponseBody> products = productService.searchByProductName(name, userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (RecordDosenotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/available/products")
  public ResponseEntity<?> showProductsList(@RequestParam UUID userId){
    try {
        List<OrderListResponseBody>products = productService.fetchProducts(userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (RecordDosenotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
