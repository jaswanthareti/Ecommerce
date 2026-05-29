package com.ecommerce.app.mapper;

import java.util.Optional;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;

public class OrderMapper {
    public static Order mapToOrder(OrderRequest orderRequest){
        return Order.builder()
                .userId(orderRequest.userId())
                .totalAmount(orderRequest.totalAmount())
                .status(orderRequest.status())
                .build();
    }

    public static OrderResponse mapToOrderResponse(Order order){
        return new OrderResponse(order.getId(), order.getUserId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus());
    }
}