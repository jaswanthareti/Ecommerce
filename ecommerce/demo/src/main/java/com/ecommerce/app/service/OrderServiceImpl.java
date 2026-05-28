package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepo;
    @Override
    public List<OrderResponse> getAll() {
        // TODO Auto-generated method stub
        return orderRepo.findAll()
                    .stream()
                    .map(OrderMapper::mapToOrderResponse)
                    .toList();
    }

    @Override
    public OrderResponse getById(Long id) {
        // TODO Auto-generated method stub
        return OrderMapper.mapToOrderResponse(orderRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No orders found with id "+id)));
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
