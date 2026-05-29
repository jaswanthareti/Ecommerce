package com.ecommerce.app.dto.request;

import java.math.BigDecimal;

import com.ecommerce.app.enums.OrderStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
    @NotNull(message = "ID cannot be null")
    Long userId,

    @NotNull(message = "Total Amount cannot be null")
    @DecimalMin(value = "0.0", message = "Total Amount must be greater than or equal to zero")
    BigDecimal totalAmount,

    @NotBlank(message = "Status cannot be blank")
    OrderStatus status
) {}