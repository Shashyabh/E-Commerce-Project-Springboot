package com.store.services;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.WishlistDto;

public interface WishlistService {

    WishlistDto addFavItem(AddItemToCartRequest cartRequest,String userId);

    WishlistDto getAllFavByUserId(String userId);
}
