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
    public List<OrderResponse> getAll() {
        // TODO Auto-generated method stub
        return orderRepository.findAll()
                    .stream()
                    .map(OrderMapper::mapToOrderResponse)
                    .toList();
    }

    @Override
    public OrderResponse getById(Long id) {
        // TODO Auto-generated method stub
        return OrderMapper.mapToOrderResponse(orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No orders found with id "+id)));
    }

    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        // TODO Auto-generated method stub
        Order order = OrderMapper.mapToOrder(orderRequest);
        return OrderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse update(Long id, OrderRequest orderRequest) {
        // TODO Auto-generated method stub
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isEmpty())
            throw new ResourceNotFoundException(String.format("Resource not found with id: %s",id));
        Order orderToUpdate = OrderMapper.mapToOrder(orderRequest);
        Order savedOrder = orderRepository.save(orderToUpdate);
        return OrderMapper.mapToOrderResponse(savedOrder);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        orderRepository.deleteById(id);
    }
    
}
