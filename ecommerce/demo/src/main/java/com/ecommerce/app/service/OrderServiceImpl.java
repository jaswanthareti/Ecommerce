package com.ecommerce.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    @Override
    public List<OrderResponse> getAllOrders() {
        // TODO Auto-generated method stub
        return orderRepository.findAll()
                    .stream()
                    .map(OrderMapper::mapToOrderResponse)
                    .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No orders found with id "+id));
        return OrderMapper.mapToOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // TODO Auto-generated method stub
        Order newOrder = OrderMapper.mapToOrder(orderRequest);
        Order savedOrder = orderRepository.save(newOrder);
        return OrderMapper.mapToOrderResponse(savedOrder);
            
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        // TODO Auto-generated method stub
        Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(()-> new ResourceNotFoundException(String.format("Resource not found with id: %s",orderId)));
        existingOrder.setUserId(orderRequest.userId());
        existingOrder.setTotalAmount(orderRequest.totalAmount());
        existingOrder.setStatus(orderRequest.status());
        Order savedOrder = orderRepository.save(existingOrder);
        return OrderMapper.mapToOrderResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        // TODO Auto-generated method stub

        Order existingOrder = orderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Order not found with id: %s", orderId)));

        orderRepository.delete(existingOrder);

    }
    
}
