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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = userRepository.findAll()
                                        .stream()
                                        .map(UserMapper::mapToUserResponse)
                                        .toList();
        
        log.info("Retrieved all users. Count = {}", users.size());
        return users;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User fetchedUser = getUserEntity(userId);
        log.info("Retrieved user successfully. userId={}",fetchedUser.getId());
        return UserMapper.mapToUserResponse(fetchedUser);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserMapper.mapToUser(userRequest);
        User savedUser = userRepository.save(user);
        log.info("User created successfully. userId={}",savedUser.getId());
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User existingUser = getUserEntity(userId);
        log.info("Fetched User. userId={}", userId);
        existingUser.setName(userRequest.name());
        existingUser.setEmail(userRequest.email());
        existingUser.setPhone(userRequest.phone());
        existingUser.setAddress(userRequest.address());
        User savedUser = userRepository.save(existingUser);
        log.info("User updated successfully. userId={}",savedUser.getId());
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = getUserEntity(userId);
        log.info("Fetched User. userId={}", userId);
        userRepository.delete(existingUser);
        log.info("User deleted successfully. userId={}",userId);
    }

    private User getUserEntity(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->{
                    log.info("Resource not found. userId={}", userId);
                    return new ResourceNotFoundException(String.format("User not found with ID %s",userId));
                    }
                );
    }
    
}
