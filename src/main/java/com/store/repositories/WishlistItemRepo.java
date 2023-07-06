package com.store.repositories;

import com.store.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepo extends JpaRepository<WishlistItem,Integer> {
}
