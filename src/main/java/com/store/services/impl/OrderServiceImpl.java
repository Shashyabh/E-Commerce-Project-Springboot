package com.store.services.impl;

import com.store.dtos.CreateOrderRequest;
import com.store.dtos.OrderDto;
import com.store.dtos.PageableResponse;
import com.store.entities.*;
import com.store.exceptions.BadApiRequest;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.repositories.CartRepo;
import com.store.repositories.OrderRepo;
import com.store.repositories.UserRepo;
import com.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId=orderDto.getUserId();
        String cartId=orderDto.getCartId();
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //Fetch cart
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItem> cartItem = cart.getCartItem();

        if(cartItem.size()<=0){
            throw new BadApiRequest("Invalid no of cartitems in Cart");
        }

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //OrderItems and AMount has not been set Yet
        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItem.stream().map(items -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(items.getQuantity())
                    .product(items.getProduct())
                    .totalPrice(items.getQuantity() * items.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderAmount(orderAmount.get());
        order.setOrderItems(orderItems);

        cart.getCartItem().clear();
        cartRepo.save(cart);
        Order savedOrder = orderRepo.save(order);

        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found"));
        orderRepo.delete(order);
    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = orderRepo.findByUser(user);

        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepo.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }
}
