package com.ecommerce.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.mapper.ProductMapper;
import com.ecommerce.app.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for product management operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
    
        log.info(
            "action=getAllProducts status=started page={} size={}",
            pageable.getPageNumber(),
            pageable.getPageSize()
        );
    
        Page<ProductResponse> products =
                productRepository.findAll(pageable)
                        .map(ProductMapper::mapToProductResponse);
    
        log.info(
            "action=getAllProducts status=success totalElements={} totalPages={}",
            products.getTotalElements(),
            products.getTotalPages()
        );
    
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(Long productId) {
        Product fetchedProduct = getProductEntity(productId);
        
        log.info(
            "action=getProductById status=success productId={}",
            fetchedProduct.getId()
        );

        return ProductMapper.mapToProductResponse(fetchedProduct);
    }

    
    /**
     * Creates a new product.
     *
     * Request body is validated before processing.
     *
     * @param productRequest validated product payload
     * @return created product details
     */

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = ProductMapper.mapToProduct(productRequest);
        Product createdProduct = productRepository.save(newProduct);
        log.info(
            "action=createProduct status=success productId={}",
            createdProduct.getId()
        );
        return ProductMapper.mapToProductResponse(createdProduct);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(
        Long productId, 
        ProductRequest productRequest
    ) {
        Product existingProduct = getProductEntity(productId);
        log.info(
            "action=updateProduct status=started productId={}",
            productId
        );
        
        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setStockQuantity(productRequest.stockQuantity());
        existingProduct.setCategory(productRequest.category());

        Product savedProduct = productRepository.save(existingProduct);
        log.info(
            "action=updateProduct status=success productId={}",
            savedProduct.getId()
        );
        
        return ProductMapper.mapToProductResponse(savedProduct);
        
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = getProductEntity(productId);
        log.info(
            "action=deleteProduct status=started productId={}",
            productId
        );
        
        productRepository.delete(existingProduct);
        log.info(
            "action=deleteProduct status=success productId={}",
            productId
        );
        
        
    }

    private Product getProductEntity(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(
                    ()-> {
                        
                        log.warn(
                            "action=getProductEntity status=failed reason=product_not_found productId={}",productId
                        );

                        return new ResourceNotFoundException(String.format("Product not found with id: %s", productId));
                    });
    }
    
}
