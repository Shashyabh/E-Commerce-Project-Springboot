package com.store.services;

import com.store.dtos.CreateOrderRequest;
import com.store.dtos.OrderDto;
import com.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //Create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //Remove order

    void removeOrder(String orderId);

    //Get order of User

    List<OrderDto> getOrderOfUser(String userId);

    //Get All orders

    PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy,String sortDir);
}
