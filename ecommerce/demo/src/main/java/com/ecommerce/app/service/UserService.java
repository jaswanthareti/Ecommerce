package com.ecommerce.app.service;

import java.util.List;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long userId);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long userId, UserRequest userRequest);
    void deleteUser(Long userId);
}
