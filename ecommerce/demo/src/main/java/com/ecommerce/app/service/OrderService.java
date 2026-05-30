package com.ecommerce.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;

public interface OrderService {
    Page<OrderResponse> getAllOrders(Pageable pageable);
    OrderResponse getOrderById(Long orderId);
    OrderResponse createOrder(OrderRequest request);
    OrderResponse updateOrder(Long orderId, OrderRequest request);
    void deleteOrder(Long orderId);
}
