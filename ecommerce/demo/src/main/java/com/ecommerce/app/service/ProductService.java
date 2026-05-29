package com.ecommerce.app.service;

import java.util.List;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long ProductId, ProductRequest ProductRequest);
    void deleteProduct(Long ProductId);
}
