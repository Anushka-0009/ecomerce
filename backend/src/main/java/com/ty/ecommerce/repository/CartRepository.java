package com.ty.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ty.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}