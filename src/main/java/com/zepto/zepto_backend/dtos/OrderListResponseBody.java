package com.zepto.zepto_backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponseBody {

  private String productName;
  private String manufacturerName;
  private String imglink;
  private double baseprice;
  private double discount;
  private int totalQuantity;

}
