package com.store.controllers;

import com.store.dtos.ApiResponse;
import com.store.dtos.CreateOrderRequest;
import com.store.dtos.OrderDto;
import com.store.dtos.PageableResponse;
import com.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request){
        OrderDto orderDto = this.orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable String orderId){
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder()
                .message("Order is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //Get order of Particular user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId){
        List<OrderDto> orderDtos = this.orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderDtos,HttpStatus.OK);
    }

    //Get all orders

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrder(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "orderedDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir

    ){
        PageableResponse<OrderDto> orderDtos = orderService.getAllOrder(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orderDtos,HttpStatus.OK);
    }
}
