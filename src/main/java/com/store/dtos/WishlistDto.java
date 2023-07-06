package com.store.dtos;

import com.store.entities.User;
import com.store.entities.WishlistItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDto {

    private String wishId;
    private UserDto user;
    private List<WishlistitemDto> wishlistItems=new ArrayList<>();
}
