package com.ecommerce.app.dto.request;

public record OrderRequest(
    Long userId,
    Double totalAmount,
    String status
) {}
