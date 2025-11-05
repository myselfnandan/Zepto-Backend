package com.zepto.zepto_backend.model;

import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "cart_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  private Cart cart;
  private UUID pid;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private double finalprice;

  private double baseprice;

  private double discount;

  public CartItem(Cart cart, UUID pid, int quantity, double finalPrice) {
    this.cart = cart;
    this.pid = pid;
    this.quantity = quantity;
    this.finalprice = finalPrice;
  }


}
