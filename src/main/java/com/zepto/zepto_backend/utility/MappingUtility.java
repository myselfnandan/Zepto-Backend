package com.zepto.zepto_backend.utility;

import org.springframework.stereotype.Component;

import com.zepto.zepto_backend.dtos.AdminRequestBody;
import com.zepto.zepto_backend.dtos.ConsumerRequestBody;
import com.zepto.zepto_backend.dtos.OrderListResponseBody;
import com.zepto.zepto_backend.dtos.ProductRequestBody;
import com.zepto.zepto_backend.dtos.WareHouseProductRequestBody;
import com.zepto.zepto_backend.dtos.WareHouseRequestBody;
import com.zepto.zepto_backend.enums.UserStatus;
import com.zepto.zepto_backend.enums.UserType;
import com.zepto.zepto_backend.model.CartItem;
import com.zepto.zepto_backend.model.Location;
import com.zepto.zepto_backend.model.Product;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.model.WareHouseItem;

@Component
public class MappingUtility {
  
  public User mapConsumerRBToUser(ConsumerRequestBody consumerRequestBody){
    User user = new User();

    user.setUsertype(UserType.CONSUMER.toString());
    user.setUsername(consumerRequestBody.getUsername());
    user.setEmail(consumerRequestBody.getEmail());
    user.setPassword(consumerRequestBody.getPassword());
    user.setNumber(consumerRequestBody.getNumber());
    user.setStatus(UserStatus.ACTIVE.toString());
    return user;
  }

  public Location mapLocationRBToUser(ConsumerRequestBody consumerRequestBody, User user){
    Location location = new Location();

    location.setAddressLine1(consumerRequestBody.getAddressLine1());
    location.setAddressLine2(consumerRequestBody.getAddressLine2());
    location.setAddressLine3(consumerRequestBody.getAddressLine3());
    location.setCity(consumerRequestBody.getCity());
    location.setState(consumerRequestBody.getState());
    location.setCountry(consumerRequestBody.getCountry());
    location.setPincode(consumerRequestBody.getPincode());
    location.setPrimary(consumerRequestBody.isPrimary());
    location.setUser(user);

    return location;
  }

  public User mapAdminToUser(AdminRequestBody adminRequestBody, String userType){
    User user = new User();
    
    user.setStatus(UserStatus.INACTIVE.toString());
    user.setUsername(adminRequestBody.getUsername());
    user.setEmail(adminRequestBody.getEmail());
    user.setPassword("Admin-1");
    if(userType.equals(UserType.ZEPTO_APP_ADMIN.toString())){
      user.setUsertype(UserType.ZEPTO_APP_ADMIN.toString());
    }else{
      user.setUsertype(UserType.WAREHOUSE_ADMIN.toString());
    }
    
    user.setNumber(adminRequestBody.getNumber());

    return user;
  }

  public WareHouse mapWareHouseRBToWareHouse(WareHouseRequestBody wareHouseRequestBody){
    WareHouse wareHouse = new WareHouse();
    wareHouse.setEmail(wareHouseRequestBody.getWareHouseEmail());
    wareHouse.setNumber(wareHouseRequestBody.getWareHouseNumber());
    return wareHouse;
  }

  public Location mapWareHouseLocationToLocation(WareHouseRequestBody wareHouseRequestBody){
    Location location = new Location();
    location.setAddressLine1(wareHouseRequestBody.getAddressLine1());
    location.setAddressLine2(wareHouseRequestBody.getAddressLine2());
    location.setAddressLine3(wareHouseRequestBody.getAddressLine3());
    location.setCity(wareHouseRequestBody.getCity());
    location.setCountry(wareHouseRequestBody.getCountry());
    location.setState(wareHouseRequestBody.getState());
    location.setPincode(wareHouseRequestBody.getPincode());
    return location;
  }

  public Product mapProductRBToProduct(ProductRequestBody productRequestBody){
    Product product = new Product();
    product.setProductName(productRequestBody.getProductName());
    product.setManufacturerName(productRequestBody.getManufacturerName());
    product.setQuantity(productRequestBody.getQuantity());
    product.setBaseprice(productRequestBody.getBaseprice());
    product.setImglink(productRequestBody.getImglink());
    return product;
  }

  public WareHouseItem mapProductToWareHouse(WareHouseProductRequestBody wareHouseProductRequestBody){
    WareHouseItem wareHouseItem = new WareHouseItem();
    wareHouseItem.setPid(wareHouseProductRequestBody.getPid());
    wareHouseItem.setWid(wareHouseProductRequestBody.getWid());
    wareHouseItem.setBaseprice(wareHouseProductRequestBody.getBaseprice());
    wareHouseItem.setDiscount(wareHouseProductRequestBody.getDiscount());
    wareHouseItem.setTotalQuantity(wareHouseProductRequestBody.getTotalQuantity());
    return wareHouseItem;
  }

  public OrderListResponseBody mapCartItemToOrderListRB(CartItem cartItem, Product product) {
    OrderListResponseBody responseBody = new OrderListResponseBody();
    
    responseBody.setProductName(product.getProductName());
    responseBody.setImglink(product.getImglink());
    responseBody.setManufacturerName(product.getManufacturerName());
    responseBody.setBaseprice(cartItem.getFinalprice());
    responseBody.setDiscount(cartItem.getDiscount());
    responseBody.setTotalQuantity(cartItem.getQuantity());
    return responseBody;
}

}
