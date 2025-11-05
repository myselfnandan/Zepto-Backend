package com.zepto.zepto_backend.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String country;
  private String state;
  private String city;
  private boolean isPrimary;
  private int pincode;

  @ManyToOne
  private User user;
}
