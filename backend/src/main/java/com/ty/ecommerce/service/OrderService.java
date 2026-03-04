package com.ty.ecommerce.service;

import org.springframework.stereotype.Service;
import com.ty.ecommerce.entity.Orders;
import com.ty.ecommerce.entity.User;
import com.ty.ecommerce.entity.Product;
import com.ty.ecommerce.dto.OrderRequest;
import com.ty.ecommerce.repository.OrderRepository;
import com.ty.ecommerce.repository.UserRepository;
import com.ty.ecommerce.repository.ProductRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Orders placeOrder(OrderRequest request) {

        System.out.println("ProductName: " + request.getProductName());
        System.out.println("UserId: " + request.getUserId());
        System.out.println("ProductId: " + request.getProductId());
        System.out.println("Quantity: " + request.getQuantity());

        Orders order = new Orders();

        User user = userRepository.findById(request.getUserId().intValue()).orElse(null);
        Product product = productRepository.findById(request.getProductId().intValue()).orElse(null);

        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(request.getQuantity() * (product != null ? product.getPrice() : 1000));

        order.setProductName(request.getProductName());
        order.setProductImage(request.getProductImage());
        order.setStatus("ORDERED");

        return orderRepository.save(order);
    }

    public List<Orders> getOrdersByUser(Integer userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public Orders cancelOrder(Long orderId, String reason) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus("CANCELLED");
            order.setCancellationReason(reason);
            return orderRepository.save(order);
        }
        return null;
    }
}