package com.zepto.zepto_backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zepto.zepto_backend.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID>{
  @Query(value = "select * from cart_item where cart_id = :cartId and pid = :pid", nativeQuery = true)
  public Optional<CartItem> findByCartIdAndPid(UUID cartId, UUID pid);
}
