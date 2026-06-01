package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;

/**
 * Service interface for product management operations.
 */
public interface ProductService {

    /**
     * Retrieves paginated products.
     *
     * @param pageable pagination details
     * @return paginated products
     */
    Page<ProductResponse> getAllProducts(Pageable pageable);

    /**
     * Retrieves product by identifier.
     *
     * @param productId product identifier
     * @return product details
     */
    ProductResponse getProductById(Long productId);

    /**
     * Creates new product.
     *
     * @param request request payload
     * @return created product
     */
    ProductResponse createProduct(ProductRequest request);

    /**
     * Updates existing product.
     *
     * @param productId product identifier
     * @param request updated payload
     * @return updated product
     */
    ProductResponse updateProduct(Long productId,
                                  ProductRequest request);

    /**
     * Deletes product by identifier.
     *
     * @param productId product identifier
     */
    void deleteProduct(Long productId);
}