package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        // TODO Auto-generated method stub
        return 
    }

    @Override
    public ProductResponse getProductById(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ProductResponse updateProduct(Long ProductId, ProductRequest ProductRequest) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteProduct(Long ProductId) {
        // TODO Auto-generated method stub
        
    }
    
}
