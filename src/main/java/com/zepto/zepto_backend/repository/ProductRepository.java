package com.zepto.zepto_backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zepto.zepto_backend.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product , UUID>{

  @Query(value = "select * from product where product_name like %:name%", nativeQuery = true)
  public List<Product> searchProduct(String name);
} 