package com.zepto.zepto_backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zepto.zepto_backend.model.Cart;

public interface CartRepository extends JpaRepository<Cart, UUID>{

  Optional<Cart> findByUserId(UUID userId);
} 