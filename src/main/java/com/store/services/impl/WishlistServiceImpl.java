package com.store.services.impl;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.WishlistDto;
import com.store.dtos.WishlistitemDto;
import com.store.entities.*;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.ProductRepo;
import com.store.repositories.UserRepo;
import com.store.repositories.WishlistItemRepo;
import com.store.repositories.WishlistRepo;
import com.store.services.WishlistService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepo wishlistRepo;

    @Autowired
    private WishlistItemRepo wishlistItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    private Logger logger= LoggerFactory.getLogger(WishlistServiceImpl.class);

    @Override
    public WishlistDto addFavItem(AddItemToCartRequest cartRequest,String userId) {
        String productId = cartRequest.getProductId();

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Wishlist wishlist = wishlistRepo.findByUser(user).orElseGet(() -> {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setWishId(UUID.randomUUID().toString());
            newWishlist.setUser(user);
            return newWishlist;
        });

        WishlistItem wishlistItem = WishlistItem.builder()
                .product(product)
                .wishlist(wishlist)
                .build();

        wishlist.getWishlistItems().add(wishlistItem);

        Wishlist savedWishlist = wishlistRepo.save(wishlist);
        WishlistDto wishlistDto = this.modelMapper.map(savedWishlist, WishlistDto.class);
        wishlistDto.setWishlistItems(
                savedWishlist.getWishlistItems().stream()
                        .map(wish -> this.modelMapper.map(wish, WishlistitemDto.class))
                        .collect(Collectors.toList())
        );

        return wishlistDto;
    }

    @Override
    public WishlistDto getAllFavByUserId(String userId) {
        Wishlist wishlist = this.wishlistRepo.getAllWishlistByUser(userId);
        return modelMapper.map(wishlist,WishlistDto.class);
    }
}
