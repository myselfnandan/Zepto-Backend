package com.zepto.zepto_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.ConsumerRequestBody;
import com.zepto.zepto_backend.model.Location;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.utility.MappingUtility;

@Service
public class ConsumerService {

  @Autowired
  MappingUtility mappingUtility;
  @Autowired
  UserService userService;
  @Autowired
  LocationService locationService;


  public void createCustomer(ConsumerRequestBody consumerRequestBody){
    //call mapping method
    User consumer =  mappingUtility.mapConsumerRBToUser(consumerRequestBody);
    consumer = userService.saveUser(consumer);
    //call location mapping method

    Location location = mappingUtility.mapLocationRBToUser(consumerRequestBody, consumer);
    locationService.saveLocation(location);
  }

}
