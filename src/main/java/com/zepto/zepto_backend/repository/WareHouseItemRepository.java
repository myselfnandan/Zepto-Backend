package com.zepto.zepto_backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zepto.zepto_backend.model.WareHouseItem;

public interface WareHouseItemRepository extends JpaRepository<WareHouseItem, UUID>{

  @Query(value = "select * from warehouseitem where wid = :wid and pid = :pid", nativeQuery = true)
 public Optional<WareHouseItem> getWareHouseItem(UUID wid, UUID pid);

 List<WareHouseItem> findByWid(UUID wid);
  
}
