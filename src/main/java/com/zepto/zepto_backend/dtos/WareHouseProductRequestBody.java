package com.zepto.zepto_backend.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WareHouseProductRequestBody {
  private UUID wid;
  private UUID pid;
  private double baseprice;
  private double discount;
  private int totalQuantity;
  
}
