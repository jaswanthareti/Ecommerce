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

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;
import com.ecommerce.app.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        log.info("action=getAllProducts status=started");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        log.info("action=createProduct status=started");
        final ProductResponse createdProduct = productService.createProduct(productRequest);
        log.info("action=createProduct status=success productId={}",createdProduct.id());
        URI location = URI.create("/api/v1/products/" + createdProduct.id());
        return ResponseEntity.created(location).body(createdProduct);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable @Positive Long productId){
        log.info("action=getProductById status=started productId={}",productId);
        final ProductResponse product = productService.getProductById(productId);
        log.info("action=getProductById status=success productId={}",productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable @Positive Long productId, @Valid @RequestBody ProductRequest productRequest){
        log.info("action=updateProduct status=started productId={}", productId);
        final ProductResponse updatedProduct = productService.updateProduct(productId, productRequest);
        log.info("action=updateProduct status=success productId={}", updatedProduct.id());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Positive Long productId){
        log.info("action=deleteProduct status=started productId={}",productId);
        productService.deleteProduct(productId);
        log.info("action=deleteProduct status=success productId={}",productId);
        return ResponseEntity.noContent().build();
    }
}
