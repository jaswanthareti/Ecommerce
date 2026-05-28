package com.ecommerce.app.service;

import java.util.List;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;

public interface OrderService {
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long orderId);
    OrderResponse createOrder(OrderRequest request);
    OrderResponse updateOrder(Long orderId, OrderRequest request);
    void deleteOrder(Long orderId);
}
