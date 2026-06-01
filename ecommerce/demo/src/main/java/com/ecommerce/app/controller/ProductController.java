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

import com.ecommerce.app.dto.request.ProductRequest;
import com.ecommerce.app.dto.response.ProductResponse;
import com.ecommerce.app.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing products.
 *
 * Provides endpoints for:
 * - creating products
 * - retrieving products
 * - updating products
 * - deleting products
 */

@RestController
@RequestMapping(
        value = "/api/v1/products",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves all products in paginated format.
     *
     * @param pageable pagination details
     * @return paginated products
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        log.info(
                "action=getAllProducts status=request_received page={} size={} sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        final Page<ProductResponse> products =
                productService.getAllProducts(pageable);

        return ResponseEntity.ok(products);
    }

    /**
     * Creates a new product.
     *
     * @param productRequest validated request payload
     * @return created product response
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest
    ) {

        log.info("action=createProduct status=started");

        final ProductResponse createdProduct =
                productService.createProduct(productRequest);

        log.info(
                "action=createProduct status=success productId={}",
                createdProduct.id()
        );

        URI location =
                URI.create("/api/v1/products/" + createdProduct.id());

        return ResponseEntity
                .created(location)
                .body(createdProduct);
    }

    /**
     * Retrieves product by identifier.
     *
     * @param productId product identifier
     * @return product details
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable @Positive Long productId
    ) {

        log.info(
                "action=getProductById status=request_received productId={}",
                productId
        );

        final ProductResponse product =
                productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    /**
     * Updates existing product.
     *
     * @param productId product identifier
     * @param productRequest updated request payload
     * @return updated product response
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable @Positive Long productId,
            @Valid @RequestBody ProductRequest productRequest
    ) {

        log.info(
                "action=updateProduct status=request_received productId={}",
                productId
        );

        final ProductResponse updatedProduct =
                productService.updateProduct(productId, productRequest);

        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes product by identifier.
     *
     * @param productId product identifier
     * @return no content response
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable @Positive Long productId
    ) {

        log.info(
                "action=deleteProduct status=request_received productId={}",
                productId
        );

        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}