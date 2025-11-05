package com.zepto.zepto_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.OrderListResponseBody;
import com.zepto.zepto_backend.dtos.ProductRequestBody;
import com.zepto.zepto_backend.dtos.ProductResponseBody;
import com.zepto.zepto_backend.exception.RecordDosenotExistException;
import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.model.Product;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.model.WareHouseItem;
import com.zepto.zepto_backend.repository.ProductRepository;
import com.zepto.zepto_backend.repository.WareHouseItemRepository;
import com.zepto.zepto_backend.utility.MappingUtility;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  MappingUtility mappingUtility;

  @Autowired
  UserService userService;

  @Autowired
  HelperForSearchService helperForSearchService;

  @Autowired
  WareHouseItemRepository wareHouseItemRepository;


  public void createProduct(ProductRequestBody productRequestBody, UUID userId){

    User user = userService.getUserById(userId);

    if(user == null){
      throw new UserNotFoundException("User With ID %s Not Found"+userId.toString());
    }
    
    if(!userService.isMaint(user) && !userService.isAdmin(user)){
      throw new UnauthorizedException("User With ID %s Cannot Perform This Operation"+userId.toString());
    }

    Product product = mappingUtility.mapProductRBToProduct(productRequestBody);
    this.saveProduct(product);
    
  }

  public Product saveProduct(Product product){
    return this.productRepository.save(product);
  }

  public Product getProductById(UUID id){
    return productRepository.findById(id).orElse(null);
  }

  public List<ProductResponseBody> searchByProductName(String name, UUID userId){
    //call repo
    List<Product> products =  productRepository.searchProduct(name);
    //get user address to check if product is available there are not
    WareHouse wareHouse = helperForSearchService.getWareHouseByPinCode(userId);
    UUID wid = null;
    if(wareHouse != null){
      wid = wareHouse.getId();
    }
    List<ProductResponseBody> productResponseBodies = new ArrayList<>();
    for(Product product : products){
      ProductResponseBody productResponseBody = new ProductResponseBody();
      UUID pid = product.getId();
      productResponseBody.setProductName(product.getProductName());
      productResponseBody.setManufacturerName(product.getManufacturerName());
      productResponseBody.setImglink(product.getImglink());
      if(wid == null){
        productResponseBody.setBaseprice(product.getBaseprice());
        productResponseBody.setDiscount(0.0);
        productResponseBody.setTotalQuantity(product.getQuantity());
        productResponseBody.setWid(null);
        productResponseBody.setAvailable(false);
      }else{
        Optional<WareHouseItem> wareHouseItemOpt = wareHouseItemRepository.getWareHouseItem(wid, pid);
        if(wareHouseItemOpt.isPresent()){
          WareHouseItem wareHouseItem = wareHouseItemOpt.get();
          productResponseBody.setBaseprice(wareHouseItem.getBaseprice());
          productResponseBody.setDiscount(wareHouseItem.getDiscount());
          productResponseBody.setPid(wareHouseItem.getPid());
          productResponseBody.setAvailable(true);
          productResponseBody.setTotalQuantity(wareHouseItem.getTotalQuantity());
          productResponseBody.setWid(wid);
        }else{
          productResponseBody.setBaseprice(product.getBaseprice());
          productResponseBody.setDiscount(0.0);
          productResponseBody.setTotalQuantity(product.getQuantity());
          productResponseBody.setPid(null);
          productResponseBody.setAvailable(false);
        }
        
      }
      productResponseBodies.add(productResponseBody);
    }
    return productResponseBodies;
  }

  public List<OrderListResponseBody> fetchProducts(UUID userId){
    WareHouse wareHouse = helperForSearchService.getWareHouseByPinCode(userId);
    if(wareHouse == null){
      throw new RecordDosenotExistException("Products Cannot Be Deliverd For This Location");
    }
    UUID wid = wareHouse.getId();
    List<WareHouseItem> wareHouseItems = wareHouseItemRepository.findByWid(wid);
    List<OrderListResponseBody> orderListResponseBodies = new ArrayList<>();

    for(WareHouseItem wareHouseItem : wareHouseItems){
      UUID pid = wareHouseItem.getPid();
      Optional<Product> productOpt = productRepository.findById(pid);
      if(productOpt.isPresent()){
        Product product = productOpt.get();
        OrderListResponseBody orderListResponseBody = new OrderListResponseBody();
        orderListResponseBody.setProductName(product.getProductName());
        orderListResponseBody.setManufacturerName(product.getManufacturerName());
        orderListResponseBody.setImglink(product.getImglink());
        orderListResponseBody.setBaseprice(wareHouseItem.getBaseprice());
        orderListResponseBody.setDiscount(wareHouseItem.getDiscount());
        orderListResponseBody.setTotalQuantity(wareHouseItem.getTotalQuantity());
        orderListResponseBodies.add(orderListResponseBody);
      }else{
        throw new RecordDosenotExistException("Products aren't available for now");
      }
    }
    return orderListResponseBodies;
  }
}
