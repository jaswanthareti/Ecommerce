package com.ecommerce.app.dto.response;

public record ProductResponse(
    Long id,
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String category
) {}
