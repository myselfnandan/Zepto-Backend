package com.zepto.zepto_backend.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zepto.zepto_backend.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID>{

  @Query(value = "select * from location where user_id = :userId and is_primary = true", nativeQuery = true)
  public Location getLocationById(UUID userId);
  
}
