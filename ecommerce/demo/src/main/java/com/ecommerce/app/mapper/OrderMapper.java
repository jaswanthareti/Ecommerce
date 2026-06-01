package com.ecommerce.app.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderItemResponse;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.entity.OrderItem;

public class OrderMapper {
    public static Order mapToOrder(OrderRequest request) {

        Order order = Order.builder()
                .userId(request.userId())
                .totalAmount(request.totalAmount())
                .status(request.status())
                .orderDate(LocalDateTime.now())
                .build();
    
        List<OrderItem> orderItems =
                request.orderItems()
                        .stream()
                        .map(itemRequest -> {
    
                            OrderItem orderItem =
                                    OrderItem.builder()
                                            .productId(itemRequest.productId())
                                            .quantity(itemRequest.quantity())
                                            .price(itemRequest.price())
                                            .order(order)
                                            .build();
    
                            return orderItem;
                        })
                        .toList();
    
        order.setOrderItems(orderItems);
    
        return order;
    }

    public static OrderResponse mapToOrderResponse(Order order){
        
    List<OrderItemResponse> orderItems =
                                order.getOrderItems()
                                    .stream()
                                    .map(item -> new OrderItemResponse(
                                            item.getId(),
                                            item.getProductId(),
                                            item.getQuantity(),
                                            item.getPrice()
                                    ))
                                    .toList();

        return new OrderResponse(
            order.getId(),
            order.getUserId(),
            order.getOrderDate(),
            order.getTotalAmount(),
            order.getStatus(),
            orderItems
        );

    }

    
}