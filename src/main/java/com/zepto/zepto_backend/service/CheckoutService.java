package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.CheckoutRequestBody;
import com.zepto.zepto_backend.model.Cart;
import com.zepto.zepto_backend.model.CartItem;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.model.WareHouseItem;
import com.zepto.zepto_backend.repository.CartRepository;
import com.zepto.zepto_backend.repository.WareHouseItemRepository;

@Service
public class CheckoutService {
  @Autowired
  CartRepository cartRepository;

  @Autowired
  HelperForSearchService helperForSearchService;

  @Autowired
  WareHouseItemRepository wareHouseItemRepository;

  public String placeOrder(CheckoutRequestBody checkoutRequestBody, UUID userId){
    if(!"COD".equalsIgnoreCase(checkoutRequestBody.getPaymentMethod())){
      throw new RuntimeException("You Can Only Choose COD For Now");
    }

    Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
    for(CartItem item : cart.getCartItems()){
      WareHouse wareHouse = helperForSearchService.getWareHouseByPinCode(userId);
      if(wareHouse == null){
        throw new RuntimeException("Ware House Not Found");
      }

      UUID wid = wareHouse.getId();
      WareHouseItem wareHouseItem = wareHouseItemRepository.getWareHouseItem(wid, item.getPid())
                                                            .orElseThrow(() -> new RuntimeException("Ware House Item Not Found"));
      int newQuantity = wareHouseItem.getTotalQuantity() - item.getQuantity();
      if(newQuantity < 0){
        throw new RuntimeException("Warehouse has 0 proudcts left %s%"+item.getPid());
      }

      wareHouseItem.setTotalQuantity(newQuantity);
      wareHouseItemRepository.save(wareHouseItem);
    }
    cartRepository.delete(cart);
    return "Order Placed SuccesFully";
  }
}
