package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.request.UserRequest;
import com.ecommerce.app.dto.response.UserResponse;
import com.ecommerce.app.entity.User;

public class UserMapper {
    public static UserResponse mapToUserResponse(User user){
        return new UserResponse(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getAddress(),user.getCreatedAt());
    }

    public static User mapToUser(UserRequest userRequest){
        return User.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .phone(userRequest.phone())
                .address(userRequest.address())
                .build();
    }
}