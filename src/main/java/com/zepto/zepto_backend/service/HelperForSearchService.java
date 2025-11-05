package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.model.Location;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.repository.WareHouseRepository;

@Service
public class HelperForSearchService {
  @Autowired
  LocationService locationService;

  @Autowired
  WareHouseRepository wareHouseRepository;

  public WareHouse getWareHouseByPinCode(UUID userId){
    Location location = locationService.getLocationByUserId(userId);
    int pincode = location.getPincode();
    return wareHouseRepository.findWareHouseByPincode(pincode);
  }
}
