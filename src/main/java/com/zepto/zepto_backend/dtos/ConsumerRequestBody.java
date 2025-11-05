package com.zepto.zepto_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConsumerRequestBody {

  private String username;
  private String email;
  private String password;
  private Long number;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String country;
  private String state;
  private String city;
  private boolean isPrimary;
  private int pincode;
}
