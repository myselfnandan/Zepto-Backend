package com.zepto.zepto_backend.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checkout")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  UUID userId;
  String paymentMethod;
}
