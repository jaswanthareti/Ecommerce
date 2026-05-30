package com.ecommerce.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
    @NotNull(message = "Name cannot be null")
    String name,

    @Email
    String email,

    @Pattern(regexp = "^[0-9]{10}", message = "Phone number must be 10 digits")
    String phone,

    @NotNull(message = "Address cannot be null")
    String address
) {}
