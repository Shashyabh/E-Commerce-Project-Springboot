package com.store.repositories;

import com.store.entities.Cart;
import com.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,String > {
    Optional<Cart> findByUser(User user);
}
