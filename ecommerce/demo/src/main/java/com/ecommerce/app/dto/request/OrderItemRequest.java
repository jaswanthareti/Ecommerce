package com.ecommerce.app.dto.request;

import java.math.BigDecimal;

public record OrderItemRequest(

    Long productId,

    Integer quantity,

    BigDecimal price

){}