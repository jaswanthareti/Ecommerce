package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;
import com.ecommerce.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return null;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }
    
}
