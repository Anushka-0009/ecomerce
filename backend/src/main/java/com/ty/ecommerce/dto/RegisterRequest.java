package com.ty.ecommerce.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    private String email;
    private String contactNo;
    private String address;
    private String profilePic;
}
