package com.ecommerce.app.dto.response;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserResponse(

        Long id,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @Pattern(regexp = "^[0-9]{10}", message = "Phone number must have 10 digits")
        String phone,

        @Email
        String email,

        @NotBlank(message = "Address can't be empty")
        String address,

        LocalDateTime createdAt
) {}
