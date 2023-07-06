package com.store.dtos;

import com.store.entities.Cart;
import com.store.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
}
