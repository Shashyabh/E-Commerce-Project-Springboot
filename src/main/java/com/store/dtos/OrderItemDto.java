package com.store.dtos;

import com.store.entities.Order;
import com.store.entities.Product;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
    //private Order order;
}
