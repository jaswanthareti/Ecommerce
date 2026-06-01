package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.app.dto.request.OrderRequest;
import com.ecommerce.app.dto.response.OrderResponse;

/**
 * Service interface for order management operations.
 */
public interface OrderService {

    /**
     * Retrieves paginated orders.
     *
     * @param pageable pagination details
     * @return paginated order response
     */
    Page<OrderResponse> getAllOrders(Pageable pageable);

    /**
     * Retrieves order by identifier.
     *
     * @param orderId order identifier
     * @return order details
     */
    OrderResponse getOrderById(Long orderId);

    /**
     * Creates new order.
     *
     * @param request request payload
     * @return created order
     */
    OrderResponse createOrder(OrderRequest request);

    /**
     * Updates existing order.
     *
     * @param orderId order identifier
     * @param request updated payload
     * @return updated order
     */
    OrderResponse updateOrder(
        Long orderId,
        OrderRequest request
    );

    /**
     * Deletes order by identifier.
     *
     * @param orderId order identifier
     */
    void deleteOrder(Long orderId);
}