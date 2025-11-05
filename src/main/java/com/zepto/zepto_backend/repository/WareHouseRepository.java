package com.zepto.zepto_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zepto.zepto_backend.model.WareHouse;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, UUID>{

  public WareHouse findWareHouseByPincode(int pincode);
  
}