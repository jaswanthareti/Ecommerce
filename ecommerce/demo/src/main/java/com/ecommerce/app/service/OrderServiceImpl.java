package com.ecommerce.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        log.info("action=getAllOrders status=started page={} size={}",
                    pageable.getPageNumber(),
                    pageable.getPageSize()
        );
        Page<OrderResponse> orderResponses = orderRepository.findAll(pageable)
                                                .map(OrderMapper::mapToOrderResponse);
        log.info("action=getAllOrders status=success totalElements={} totalPages={}",
                    orderResponses.getNumberOfElements(),
                    orderResponses.getTotalPages()
        );
        return orderResponses;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order fetchedOrder = getOrderEntity(orderId);
        log.info("Retrieved order succeesfully. orderId={}", orderId);
        return OrderMapper.mapToOrderResponse(fetchedOrder);
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {        
        Order newOrder = OrderMapper.mapToOrder(orderRequest);
        Order savedOrder = orderRepository.save(newOrder);
        log.info("Order created successfully. orderId={} userId={}", savedOrder.getId(), savedOrder.getUserId());
        return OrderMapper.mapToOrderResponse(savedOrder);
            
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order existingOrder = getOrderEntity(orderId);
        log.info("Updating order. orderId={}", existingOrder.getId());
        existingOrder.setUserId(orderRequest.userId());
        existingOrder.setTotalAmount(orderRequest.totalAmount());
        existingOrder.setStatus(orderRequest.status());
        Order savedOrder = orderRepository.save(existingOrder);
        log.info("Order updated successfully. orderId={}",savedOrder.getId());
        return OrderMapper.mapToOrderResponse(savedOrder);
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        Order existingOrder = getOrderEntity(orderId);
        log.info("Deleting order. orderId={}", existingOrder.getId());
        orderRepository.delete(existingOrder);
        log.info("Order deleted successfully. orderId={}", existingOrder.getId());
    }

    private Order getOrderEntity(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> {
                log.info("Order not found. orderId={}",orderId);
                return new ResourceNotFoundException(String.format("Resource not found with id: %s",orderId));
            });
    }
    
}
