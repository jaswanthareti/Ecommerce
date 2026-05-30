package com.ecommerce.app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;
import com.ecommerce.app.service.OrderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        log.info("action=getAllOrders status=started");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        log.info("action=createOrder status=started userId={}",orderRequest.userId());
        final OrderResponse createdOrder = orderService.createOrder(orderRequest);
        log.info("action=createOrder status=success orderId={}",createdOrder.id());
        URI location = URI.create("/api/v1/orders/" + createdOrder.id());
        return ResponseEntity.created(location).body(createdOrder);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable @Positive Long orderId){
        log.info("action=getOrderById status=started orderId={}", orderId);
        final OrderResponse order = orderService.getOrderById(orderId);
        log.info("action=getOrderById status=success orderId={}", orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable @Positive Long orderId, @Valid @RequestBody OrderRequest orderRequest){
        log.info("action=updateOrder status=started orderId={}", orderId);
        final OrderResponse updatedOrder = orderService.updateOrder(orderId, orderRequest);
        log.info("action=updateOrder status=success orderId={}", updatedOrder.id());
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable @Positive Long orderId){
        log.info("action=deleteOrder status=started orderId={}", orderId);
        orderService.deleteOrder(orderId);
        log.info("action=deleteOrder status=success orderId={}", orderId);
        return ResponseEntity.noContent().build();
    }
}
