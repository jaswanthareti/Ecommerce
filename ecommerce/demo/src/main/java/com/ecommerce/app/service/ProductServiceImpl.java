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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productRepository.findAll()
                                            .stream()
                                            .map(ProductMapper::mapToProductResponse)
                                            .toList();
        log.info("Retrieved all orders. Count = {}",products.size());
        return products;
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product fetchedProduct = getProductEntity(productId);
        log.info("Product retrieved successfully. productId={}",fetchedProduct.getId());
        return ProductMapper.mapToProductResponse(fetchedProduct);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = ProductMapper.mapToProduct(productRequest);
        Product createdProduct = productRepository.save(newProduct);
        log.info("Product created successfully. productId={}", createdProduct.getId());
        return ProductMapper.mapToProductResponse(createdProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product existingProduct = getProductEntity(productId);
        log.info("Retrieved existing product. productId={}",productId);
        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setStockQuantity(productRequest.stockQuantity());
        existingProduct.setCategory(productRequest.category());

        Product savedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully. productId={}",savedProduct.getId());
        return ProductMapper.mapToProductResponse(savedProduct);
        
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = getProductEntity(productId);
        log.info("Retrieved existing product. productId={}",productId);
        productRepository.delete(existingProduct);
        log.info("Product deleted successfully. productId={}", productId);
        
    }

    private Product getProductEntity(Long productId){
        return productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(String.format("Product with ID %s is not found", productId)));
    }
    
}
