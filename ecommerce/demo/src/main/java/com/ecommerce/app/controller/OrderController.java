package com.ecommerce.app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.service.OrderService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        log.info("Received GET request to retrieve all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        log.info("Received create order request from {}", orderRequest.userId());
        OrderResponse createdOrder = orderService.createOrder(orderRequest);
        log.info("Order created successfully with id {}",createdOrder.id());
        URI location = URI.create("/api/v1/orders"+createdOrder.id());
        return ResponseEntity.created(location).body(createdOrder);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId){
        log.info("Received GET request to retrieve order {}", orderId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderRequest orderRequest){
        log.info("Received PUT request to update order {}", orderId);
        OrderResponse updatedOrder = orderService.updateOrder(orderId, orderRequest);
        log.info("Order updated successfully with id {}", updatedOrder.id());
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        log.info("Received DELETE request to delete order {}", orderId);
        orderService.deleteOrder(orderId);
        log.info("Order {} deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
