package com.ty.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ty.ecommerce.entity.Orders;
import com.ty.ecommerce.dto.OrderRequest;
import com.ty.ecommerce.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/buyNow")
    public ResponseEntity<Orders> buyNow(@RequestBody OrderRequest request) {
        Orders order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUser(@PathVariable Integer userId) {
        List<Orders> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Orders> cancelOrder(@PathVariable Long orderId, @RequestBody String reason) {
        Orders cancelledOrder = orderService.cancelOrder(orderId, reason);
        return ResponseEntity.ok(cancelledOrder);
    }
}