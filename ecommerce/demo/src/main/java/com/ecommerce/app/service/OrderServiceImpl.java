package com.ecommerce.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
        .stream()
        .map(OrderMapper::mapToOrderResponse)
        .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order fetchedOrder = getOrderEntity(orderId);
        return OrderMapper.mapToOrderResponse(fetchedOrder);
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {        
        Order newOrder = OrderMapper.mapToOrder(orderRequest);
        Order savedOrder = orderRepository.save(newOrder);
        return OrderMapper.mapToOrderResponse(savedOrder);
            
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order existingOrder = getOrderEntity(orderId);
        existingOrder.setUserId(orderRequest.userId());
        existingOrder.setTotalAmount(orderRequest.totalAmount());
        existingOrder.setStatus(orderRequest.status());
        Order savedOrder = orderRepository.save(existingOrder);
        return OrderMapper.mapToOrderResponse(savedOrder);
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        Order existingOrder = getOrderEntity(orderId);
        orderRepository.delete(existingOrder);

    }

    private Order getOrderEntity(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> new ResourceNotFoundException(String.format("Resource not found with id: %s",orderId)));
    }
    
}
