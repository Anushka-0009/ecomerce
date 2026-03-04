package com.ty.ecommerce.service;

import com.ty.ecommerce.dto.LoginRequest;
import com.ty.ecommerce.dto.RegisterRequest;
import com.ty.ecommerce.dto.UserResponse;
import com.ty.ecommerce.entity.User;
import com.ty.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setContactNo(request.getContactNo());
        user.setAddress(request.getAddress());
        user.setProfilePic(request.getProfilePic());

        if ("admin".equalsIgnoreCase(request.getUsername())) {
            user.setRole("ADMIN");
        } else {
            user.setRole(request.getRole() != null ? request.getRole() : "USER");
        }

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername(), saved.getRole(),
                saved.getEmail(), saved.getContactNo(),
                saved.getAddress(), saved.getProfilePic());

    }

    public UserResponse login(LoginRequest request) {
        User dbUser = userRepository.findByUsername(request.getUsername());
        if (dbUser == null)
            return null;

        if (encoder.matches(request.getPassword(), dbUser.getPassword()) ||
                request.getPassword().equals(dbUser.getPassword())) { // Plain text fallback
            System.out.println("Login success for: " + dbUser.getUsername() + ", ID: " + dbUser.getId());
            if ("admin".equalsIgnoreCase(dbUser.getUsername()) && !"ADMIN".equals(dbUser.getRole())) {
                dbUser.setRole("ADMIN");
                userRepository.save(dbUser);
            }
            return new UserResponse(dbUser.getId(), dbUser.getUsername(), dbUser.getRole(),
                    dbUser.getEmail(), dbUser.getContactNo(),
                    dbUser.getAddress(), dbUser.getProfilePic());
        }
        return null;
    }

    public UserResponse updateProfile(int id, com.ty.ecommerce.entity.User updatedInfo) {
        System.out.println("Updating profile for user ID: " + id + ", Email: " + updatedInfo.getEmail());
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;

        user.setEmail(updatedInfo.getEmail());
        user.setContactNo(updatedInfo.getContactNo());
        user.setAddress(updatedInfo.getAddress());
        user.setProfilePic(updatedInfo.getProfilePic());

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getRole(),
                saved.getEmail(), saved.getContactNo(),
                saved.getAddress(), saved.getProfilePic());
    }

    public UserResponse getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;
        return new UserResponse(user.getId(), user.getUsername(), user.getRole(),
                user.getEmail(), user.getContactNo(),
                user.getAddress(), user.getProfilePic());
    }
}
