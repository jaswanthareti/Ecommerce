package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.UserMapper;
import com.ecommerce.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User fetchedUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(String.format("User not found with ID %s",userId)));
        return UserMapper.mapToUserResponse(fetchedUser);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserMapper.mapToUser(userRequest);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User existingUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(String.format("User not found with ID %s",userId)));
        existingUser.setName(userRequest.name());
        existingUser.setEmail(userRequest.email());
        existingUser.setPhone(userRequest.phone());
        existingUser.setAddress(userRequest.address());
        User savedUser = userRepository.save(existingUser);
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(String.format("User not found with ID %s",userId)));
        userRepository.delete(existingUser);
    }
    
}
