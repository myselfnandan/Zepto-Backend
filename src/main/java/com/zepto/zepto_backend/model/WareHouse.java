package com.zepto.zepto_backend.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wareHouse")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class WareHouse {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String email;
  private Long number;

  @Column(name = "pincode")
  private int pincode;

  @OneToOne
  private Location location;

  @OneToOne
  private User WareHouseAdmin;

  @OneToMany
  private List<WareHouseItem> wareHouseItems;
}
