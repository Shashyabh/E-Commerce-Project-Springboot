package com.store.repositories;

import com.store.entities.User;
import com.store.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishlistRepo extends JpaRepository<Wishlist,String> {

    @Query(value = "Select * from wishlists w where w.user_id=:userId",nativeQuery = true)
    Wishlist getAllWishlistByUser(@Param("userId") String userId);

    Optional<Wishlist> findByUser(User user);
}
