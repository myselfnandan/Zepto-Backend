package com.zepto.zepto_backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.WareHouseProductRequestBody;
import com.zepto.zepto_backend.dtos.WareHouseRequestBody;
import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.service.WareHouseService;


@RestController
@RequestMapping("/api/v2/warehouse")
public class WareHouseController {

  @Autowired
  WareHouseService wareHouseService;

  @PostMapping("/create")
  public ResponseEntity<String> createWareHouse(@RequestBody WareHouseRequestBody wareHouseRequestBody, @RequestParam UUID userId){
    try{
      wareHouseService.createWareHouse(wareHouseRequestBody, userId);
      return new ResponseEntity<>("Ware House Created Succesfully!", HttpStatus.CREATED);
    }catch(UserNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }catch(UnauthorizedException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }catch(Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/assign/product/{userId}")
  public ResponseEntity<String> assignProductToWareHouse(@RequestBody WareHouseProductRequestBody wareHouseProductRequestBody, @PathVariable UUID userId){
    try{
      wareHouseService.assignProductToWareHouse(wareHouseProductRequestBody, userId);
      return new ResponseEntity<>("Product Added Succesfully!", HttpStatus.CREATED);
    }catch(UserNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }catch(UnauthorizedException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }catch(Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
