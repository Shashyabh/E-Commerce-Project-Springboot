package com.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wishlistsitems")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistItemId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    private Wishlist wishlist;

}
