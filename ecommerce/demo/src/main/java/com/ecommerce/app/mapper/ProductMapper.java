package com.ecommerce.app.mapper;

import java.util.function.Function;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;
import com.ecommerce.app.entity.Product;

public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getStockQuantity(),product.getCategory());
    }

    public static Product mapToProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .stockQuantity(productRequest.stockQuantity())
                .category(productRequest.category())
                .build();
    }
    
}