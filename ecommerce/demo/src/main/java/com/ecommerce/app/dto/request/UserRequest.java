package com.ecommerce.app.dto.request;

public record UserRequest(
    String name,
    String email,
    String phone,
    String address
) {}
