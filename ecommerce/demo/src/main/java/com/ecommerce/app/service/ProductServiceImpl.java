package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.ProductMapper;
import com.ecommerce.app.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::mapToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product fetchedProduct = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(String.format("Product with ID %s is not found", productId)));
        return ProductMapper.mapToProductResponse(fetchedProduct);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = ProductMapper.mapToProduct(productRequest);
        Product createdProduct = productRepository.save(newProduct);
        return ProductMapper.mapToProductResponse(createdProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(String.format("Product with ID %s is not found", productId)));
        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setStockQuantity(productRequest.stockQuantity());
        existingProduct.setCategory(productRequest.category());

        Product savedProduct = productRepository.save(existingProduct);
        return ProductMapper.mapToProductResponse(savedProduct);
        
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(String.format("Product with ID %s is not found", productId)));

        productRepository.delete(existingProduct);
        
    }
    
}
