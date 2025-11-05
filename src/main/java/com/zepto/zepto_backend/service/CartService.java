package com.zepto.zepto_backend.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.CartRequestBody;
import com.zepto.zepto_backend.model.Cart;
import com.zepto.zepto_backend.model.CartItem;
import com.zepto.zepto_backend.model.WareHouse;
import com.zepto.zepto_backend.model.WareHouseItem;
import com.zepto.zepto_backend.repository.CartItemRepository;
import com.zepto.zepto_backend.repository.CartRepository;
import com.zepto.zepto_backend.repository.WareHouseItemRepository;


@Service
public class CartService {

  @Autowired
  CartRepository cartRepository;

  @Autowired
  CartItemRepository cartItemRepository;

  @Autowired
  HelperForSearchService helperForSearchService;

  @Autowired
  WareHouseItemRepository wareHouseItemRepository;

  public void addToCart(CartRequestBody cartRequestBody){
    UUID userId = cartRequestBody.getUserId();
    UUID pid = cartRequestBody.getPid();
    int quantityToAdd = cartRequestBody.getQuantity();

    Cart cart = cartRepository.findByUserId(userId)
    .orElseGet(() -> {
        Cart newCart = new Cart(userId);
        return cartRepository.save(newCart);
    });

    WareHouse wareHouse = helperForSearchService.getWareHouseByPinCode(userId);
    if(wareHouse == null){
      throw new RuntimeException("No warehouse found for user's pincode");
    }
    UUID wid = wareHouse.getId();

    Optional<WareHouseItem> wareHouseItemOpt = wareHouseItemRepository.getWareHouseItem(wid, pid);
    if(!wareHouseItemOpt.isPresent()){
      throw new RuntimeException("Product not available in user's warehouse");
    }
    WareHouseItem wareHouseItem = wareHouseItemOpt.get();
    double baseprice = wareHouseItem.getBaseprice();
    double discount = wareHouseItem.getDiscount();
    double priceAfterDiscount = baseprice - (baseprice * discount / 100);

    Optional<CartItem> cartItemOpt = cartItemRepository.findByCartIdAndPid(cart.getId(), pid);

    if(cartItemOpt.isPresent()){
      CartItem existingItem = cartItemOpt.get();
      existingItem.setQuantity(quantityToAdd + existingItem.getQuantity());
      cartItemRepository.save(existingItem);
    }else{
      CartItem cartItem = new CartItem();
      cartItem.setCart(cart);
      cartItem.setPid(pid);
      cartItem.setBaseprice(baseprice);
      cartItem.setDiscount(discount);
      cartItem.setFinalprice(priceAfterDiscount);
      cartItem.setQuantity(quantityToAdd);
      cartItemRepository.save(cartItem);
    }
    
  }
}
