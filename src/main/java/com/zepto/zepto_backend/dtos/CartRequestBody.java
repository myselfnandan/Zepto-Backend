package com.zepto.zepto_backend.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestBody {

  private UUID userId;
  private UUID pid;
  private int quantity;
  
}
