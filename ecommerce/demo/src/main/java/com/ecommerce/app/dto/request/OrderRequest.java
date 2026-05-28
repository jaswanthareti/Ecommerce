package com.ecommerce.app.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrderRequest(
    @NotNull(message = "ID cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "User ID must be a valid number")
    String userId,

    @NotNull(message = "Total Amount cannot be null")
    @DecimalMin(value = "0.0", message = "Total Amount must be greater than or equal to zero")
    Double totalAmount,

    @NotBlank(message = "Status cannot be null")
    String status
) {}

// curl -X POST https://24ffefab1a07444e8eda7aa277a3b7ff-2886743045-8080-host25nc.environments.katacoda.com/api/orders \
//     -H "Content-Type: application/json" \
//     -d '{"userId": 1, "totalAmount":-1, "status": "CREATED"}'