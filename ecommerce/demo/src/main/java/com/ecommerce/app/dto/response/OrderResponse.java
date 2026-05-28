package com.ecommerce.app.dto.response;

import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    Long userId,
    LocalDateTime orderDate,
    Double totalAmount,
    String status
) {}
