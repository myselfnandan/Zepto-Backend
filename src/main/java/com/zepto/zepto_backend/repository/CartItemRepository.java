package com.zepto.zepto_backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zepto.zepto_backend.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID>{
  public Optional<CartItem> findByCartIdAndPid(UUID cartId, UUID pid);
}
