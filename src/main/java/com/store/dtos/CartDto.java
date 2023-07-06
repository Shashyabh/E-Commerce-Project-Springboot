package com.store.dtos;

import com.store.entities.CartItem;
import com.store.entities.User;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date date;
    private UserDto user;
    private List<CartItem> cartItem=new ArrayList<>();
}
