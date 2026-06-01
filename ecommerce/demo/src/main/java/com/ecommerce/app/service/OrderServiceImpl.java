package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Service implementation for order management operations.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        log.info("action=getAllOrders status=started page={} size={}",
                    pageable.getPageNumber(),
                    pageable.getPageSize()
        );
        Page<OrderResponse> orderResponses = orderRepository.findAll(pageable)
                                                .map(OrderMapper::mapToOrderResponse);
        log.info("action=getAllOrders status=success totalElements={} totalPages={}",
                    orderResponses.getTotalElements(),
                    orderResponses.getTotalPages()
        );
        return orderResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order fetchedOrder = getOrderEntity(orderId);
        log.info(
            "action=getOrderById status=success orderId={}",
            orderId
        );

        return OrderMapper.mapToOrderResponse(fetchedOrder);
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {     
        log.info(
            "action=createOrder status=started userId={}",
            orderRequest.userId()
        );
        Order newOrder = OrderMapper.mapToOrder(orderRequest);
        Order savedOrder = orderRepository.save(newOrder);
        
        log.info(
            "action=createOrder status=success orderId={} userId={}",
            savedOrder.getId(),
            savedOrder.getUserId()
        );

        return OrderMapper.mapToOrderResponse(savedOrder);
            
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(
        Long orderId, 
        OrderRequest orderRequest
        ) {
        Order existingOrder = getOrderEntity(orderId);
        log.info(
            "action=updateOrder status=started orderId={}",
            existingOrder.getId()
        );
        
        existingOrder.setUserId(orderRequest.userId());
        existingOrder.setTotalAmount(orderRequest.totalAmount());
        existingOrder.setStatus(orderRequest.status());
        Order savedOrder = orderRepository.save(existingOrder);
        log.info(
            "action=updateOrder status=success orderId={}",
            existingOrder.getId()
        );
        return OrderMapper.mapToOrderResponse(savedOrder);
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        Order existingOrder = getOrderEntity(orderId);
        log.info(
            "action=deleteOrder status=started orderId={}",
            existingOrder.getId()
        );
        
        orderRepository.delete(existingOrder);
        log.info(
            "action=deleteOrder status=success orderId={}",
            existingOrder.getId()
        );
        
    }

    private Order getOrderEntity(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> {
                log.warn("Order not found. orderId={}",orderId);
                return new ResourceNotFoundException(String.format("Order not found with id: %s",orderId));
            });
    }
    
}
