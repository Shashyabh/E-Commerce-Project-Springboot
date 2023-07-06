package com.store.controllers;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.WishlistDto;
import com.store.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<WishlistDto> createWish(@RequestBody AddItemToCartRequest request,@PathVariable String userId) {
        WishlistDto wishlistDto = this.wishlistService.addFavItem(request, userId);
        return new ResponseEntity<>(wishlistDto, HttpStatus.CREATED);
    }

    @GetMapping("/getwishlist/{userId}")
    public ResponseEntity<WishlistDto> getWish(@PathVariable String userId){
        WishlistDto wishlistDto = this.wishlistService.getAllFavByUserId(userId);
        return new ResponseEntity<>(wishlistDto, HttpStatus.OK);
    }
}
