package com.store.services.impl;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.CartDto;
import com.store.entities.Cart;
import com.store.entities.CartItem;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.exceptions.BadApiRequest;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.CartItemRepo;
import com.store.repositories.CartRepo;
import com.store.repositories.ProductRepo;
import com.store.repositories.UserRepo;
import com.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        if (quantity<=0){
            throw new BadApiRequest("Requested quantity is not valid");
        }

        //fetch the product
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart=null;
        try {
            cart=cartRepo.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart=new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setDate(new Date());
        }
        //If product is already in our cart
        AtomicReference<Boolean> flag=new AtomicReference<>(false);

        List<CartItem> items = cart.getCartItem();
        items= items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getDiscountedPrice());
                flag.set(true);
            }
            return item;
        }).collect(Collectors.toList());

       // cart.setCartItem(updatedItems);

        //Perform cart operation
        if(!flag.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getCartItem().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepo.save(cart);
        return modelMapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepo.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));
        cartItemRepo.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not founnd"));
        cart.getCartItem().clear();
        cartRepo.save(cart);
    }

    @Override
    public CartDto getCartUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not founnd"));
        return modelMapper.map(cart,CartDto.class);
    }
}
