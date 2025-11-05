package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.model.Location;
import com.zepto.zepto_backend.repository.LocationRepository;

@Service
public class LocationService {
  
  @Autowired
  LocationRepository locationRepository;

  public Location getLocationByUserId(UUID userId){
    return locationRepository.getLocationById(userId);
  }

  public Location saveLocation(Location location){
    return locationRepository.save(location);
  }
}
