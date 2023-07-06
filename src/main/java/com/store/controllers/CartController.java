package com.store.controllers;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.ApiResponse;
import com.store.dtos.CartDto;
import com.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addItem/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemsFromCart(@PathVariable String userId,@PathVariable int itemId){
        cartService.removeItemFromCart(userId,itemId);
        ApiResponse response = ApiResponse.builder().message("Item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message("Cart for this user is cleared")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartDto = cartService.getCartUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

}
