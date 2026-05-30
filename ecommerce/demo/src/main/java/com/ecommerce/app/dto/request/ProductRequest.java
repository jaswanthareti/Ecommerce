package com.ecommerce.app.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotNull(message = "Name cannot be null")
    String name,

    @NotNull(message = "Description cannot be null")
    String description,
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0", message = "Price must be a positive number")
    BigDecimal price,
    
    @NotNull(message = "Stock Quantity cannot be null")
    @Min(value = 1, message = "Stock Quantity must be greater than 0")
    Integer stockQuantity,
    
    @NotNull(message = "Category cannot be null")
    String category
) {}
