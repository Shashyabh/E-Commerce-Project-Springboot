package com.store.repositories;

import com.store.entities.Order;
import com.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,String > {
    List<Order> findByUser(User user);
}
