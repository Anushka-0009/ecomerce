package com.ty.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ty.ecommerce.entity.Cart;
import com.ty.ecommerce.service.CartService;

@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add to cart
    @PostMapping
    public Cart addToCart(@RequestBody Cart cart) {

        System.out.println("Received Product:");
        System.out.println(cart.getTitle());
        System.out.println(cart.getPrice());
        System.out.println(cart.getImage());

        return cartService.addToCart(cart);
    }

    // Get cart items
    @GetMapping
    public List<Cart> getCartItems() {
        return cartService.getCartItems();
    }

    // Delete item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        cartService.removeItem(id);
    }
}