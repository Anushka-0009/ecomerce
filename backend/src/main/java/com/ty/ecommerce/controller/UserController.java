package com.ty.ecommerce.controller;

import com.ty.ecommerce.dto.LoginRequest;
import com.ty.ecommerce.dto.RegisterRequest;
import com.ty.ecommerce.dto.UserResponse;
import com.ty.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateProfile(@PathVariable int id, @RequestBody com.ty.ecommerce.entity.User user) {
        return userService.updateProfile(id, user);
    }
}
