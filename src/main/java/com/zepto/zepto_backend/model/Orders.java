package com.zepto.zepto_backend.model;

import java.time.LocalDateTime;
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
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private LocalDateTime orderPlacedTime;
  private String paymentMethod;
  
  @ManyToOne
  private User consumer;

  @ManyToOne
  private WareHouse wareHouse;
}
