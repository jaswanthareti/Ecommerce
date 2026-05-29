package com.ecommerce.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.app.enums.OrderStatus;

public record OrderResponse(
    Long id,
    Long userId,
    LocalDateTime orderDate,
    BigDecimal totalAmount,
    OrderStatus status
) {}
