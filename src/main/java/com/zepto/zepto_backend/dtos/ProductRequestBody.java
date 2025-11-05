package com.zepto.zepto_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestBody {
  private String productName;
  private String manufacturerName;
  private int quantity;
  private int baseprice;
  private String imglink;
}
