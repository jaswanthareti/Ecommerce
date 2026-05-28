package com.ecommerce.app.dto.request;

public record ProductRequest(
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String category
) {}
