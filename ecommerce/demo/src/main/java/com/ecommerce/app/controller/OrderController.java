package com.ecommerce.app.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
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

/**
 * REST controller for managing orders.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(
    value = "/api/v1/orders",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieves all orders in paginated format.
     *
     * @param pageable pagination and sorting information
     * @return paginated order response
     */
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        log.info(
                "action=getAllOrders status=request_received page={} size={} sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        final Page<OrderResponse> orders =
                orderService.getAllOrders(pageable);

        return ResponseEntity.ok(orders);
    }

    /**
     * Creates a new order.
     *
     * @param orderRequest validated request payload
     * @return created order response
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest
    ) {

        log.info(
                "action=createOrder status=started userId={}",
                orderRequest.userId()
        );

        final OrderResponse createdOrder =
                orderService.createOrder(orderRequest);

        log.info(
                "action=createOrder status=success orderId={}",
                createdOrder.id()
        );

        URI location =
                URI.create("/api/v1/orders/" + createdOrder.id());

        return ResponseEntity
                .created(location)
                .body(createdOrder);
    }

    /**
     * Retrieves order by identifier.
     *
     * @param orderId order identifier
     * @return order details
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable @Positive Long orderId
    ) {

        log.info(
                "action=getOrderById status=request_received orderId={}",
                orderId
        );

        final OrderResponse order =
                orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    /**
     * Updates an existing order.
     *
     * @param orderId order identifier
     * @param orderRequest updated request payload
     * @return updated order response
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable @Positive Long orderId,
            @Valid @RequestBody OrderRequest orderRequest
    ) {

        log.info(
                "action=updateOrder status=request_received orderId={}",
                orderId
        );

        final OrderResponse updatedOrder =
                orderService.updateOrder(orderId, orderRequest);

        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Deletes order by identifier.
     *
     * @param orderId order identifier
     * @return no content response
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable @Positive Long orderId
    ) {

        log.info(
                "action=deleteOrder status=request_received orderId={}",
                orderId
        );

        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}