package com.zepto.zepto_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WareHouseRequestBody {
  
  private String wareHouseEmail;
  private Long wareHouseNumber;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String country;
  private String state;
  private String city;
  private int pincode;

}
