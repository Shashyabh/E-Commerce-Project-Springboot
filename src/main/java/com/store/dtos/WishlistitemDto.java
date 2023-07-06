package com.store.dtos;

import com.store.entities.Product;
import com.store.entities.Wishlist;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistitemDto {

    private int wishlistItemId;
    private Product product;
    private Wishlist wishlist;
}
