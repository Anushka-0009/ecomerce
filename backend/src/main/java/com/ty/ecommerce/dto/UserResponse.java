package com.ty.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String role;
    private String email;
    private String contactNo;
    private String address;
    private String profilePic;
}
