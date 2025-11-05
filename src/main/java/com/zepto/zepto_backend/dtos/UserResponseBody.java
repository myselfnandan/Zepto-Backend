package com.zepto.zepto_backend.dtos;

import com.zepto.zepto_backend.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseBody {
  private String token;
  private User user;
  
}
