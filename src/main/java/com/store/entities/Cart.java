package com.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    private String cartId;
    private Date date;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    //Cart_items mapping
    @JsonIgnore
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItem=new ArrayList<>();
}
