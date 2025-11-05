package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.WareHouseProductRequestBody;
import com.zepto.zepto_backend.dtos.WareHouseRequestBody;
import com.zepto.zepto_backend.exception.InsufficientProductQuantityException;
import com.zepto.zepto_backend.exception.RecordDosenotExistException;
import com.zepto.zepto_backend.model.Location;
import com.zepto.zepto_backend.model.Product;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.model.WareHouseItem;
import com.zepto.zepto_backend.repository.WareHouseItemRepository;
import com.zepto.zepto_backend.repository.WareHouseRepository;
import com.zepto.zepto_backend.utility.MappingUtility;


@Service
public class WareHouseService {
  
  @Autowired
  MappingUtility mappingUtility;

  @Autowired
  LocationService locationService;

  @Autowired
  WareHouseRepository wareHouseRepository;

  @Autowired
  ProductService productService;

  @Autowired
  UserValidationService userValidationService;

  @Autowired
  WareHouseItemRepository wareHouseItemRepository;

  public void createWareHouse(WareHouseRequestBody wareHouseRequestBody, UUID userId){

    userValidationService.userValidation(userId);

    Location location = mappingUtility.mapWareHouseLocationToLocation(wareHouseRequestBody);
    location = locationService.saveLocation(location);

    WareHouse wareHouse = mappingUtility.mapWareHouseRBToWareHouse(wareHouseRequestBody);
    wareHouse.setLocation(location);
    wareHouse.setPincode(location.getPincode());
    this.SaveWareHouse(wareHouse);

  }

  public WareHouse SaveWareHouse(WareHouse wareHouse){
    return this.wareHouseRepository.save(wareHouse);
  }

  public void assignProductToWareHouse(WareHouseProductRequestBody wareHouseProductRequestBody, UUID userId){

    userValidationService.userValidation(userId);

    UUID pid =  wareHouseProductRequestBody.getPid();
    UUID wid = wareHouseProductRequestBody.getWid();
    Product product = productService.getProductById(pid);
    WareHouse wareHouse = this.getWareHouseById(wid);

    if(pid == null || wid == null){
      throw new RecordDosenotExistException("The Product Or WareHouse Deosen't Exist In The DataBase");
    }

    if(product.getQuantity() < wareHouseProductRequestBody.getTotalQuantity()){
      throw new InsufficientProductQuantityException("The Requested Quantity Is More Than Total Quantity Present");
    }

    product.setQuantity(product.getQuantity() - wareHouseProductRequestBody.getTotalQuantity());
    productService.saveProduct(product);
    WareHouseItem wareHouseItem = mappingUtility.mapProductToWareHouse(wareHouseProductRequestBody);
    wareHouseItem = this.saveWareHouseItem(wareHouseItem);
    wareHouse.getWareHouseItems().add(wareHouseItem);
    this.SaveWareHouse(wareHouse);
  }

  public WareHouseItem saveWareHouseItem(WareHouseItem wareHouseItem){
    return this.wareHouseItemRepository.save(wareHouseItem);
  }

  public WareHouse getWareHouseById(UUID id){
    return this.wareHouseRepository.findById(id).orElse(null);
  }

}
