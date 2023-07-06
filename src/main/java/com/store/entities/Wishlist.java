package com.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wishlists")
public class Wishlist {

    @Id
    private String wishId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "wishlist",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<WishlistItem> wishlistItems=new ArrayList<>();

}
