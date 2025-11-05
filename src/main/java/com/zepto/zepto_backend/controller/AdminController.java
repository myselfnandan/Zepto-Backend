package com.zepto.zepto_backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.zepto_backend.dtos.AdminRequestBody;
import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

  @Autowired
  AdminService adminService;

  @PostMapping("/invite")
  public ResponseEntity<String> inviteAdmin(@RequestBody AdminRequestBody adminRequestBody, @RequestParam UUID userId){
    try{
      adminService.inviteAdmin(adminRequestBody, userId);
      return new ResponseEntity<>("Invitation Mail Has Sent Succesfully!", HttpStatus.CREATED);
    }catch(UserNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }catch(UnauthorizedException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }catch(Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
  }

  @GetMapping("/invitation/accept/{userId}")
  public ResponseEntity<String> acceptRequest(@PathVariable UUID userId){
    try{
      adminService.adminAcceptStatus(userId);
      return new ResponseEntity<>("Admin Has Accepted The Invitation", HttpStatus.ACCEPTED);
    }catch(UserNotFoundException error){
      return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
    }catch(Exception error){
      return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/invitation/reject/{userId}")
  public ResponseEntity<String> rejectRequest(@PathVariable UUID userId){
    try{
      adminService.adminRejectStatus(userId);
      return new ResponseEntity<>("Admin has declined the request",HttpStatus.ACCEPTED);
    }catch(UserNotFoundException error){
      return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
    }catch(Exception error){
      return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
}
