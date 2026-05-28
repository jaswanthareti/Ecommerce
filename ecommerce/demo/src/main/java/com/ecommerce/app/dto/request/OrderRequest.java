package com.ecommerce.app.dto.request;

public record OrderRequest(
    @NotNull(message = "ID cannot be null")
    Long userId,

    @NotNull(message = "Total Amount cannot be null")
    @DecimalMin(value = 0, message = "Total Amount must be greater than or equal to zero")
    Double totalAmount,

    @NotBlank(message = "Status cannot be null")
    String status
) {}
