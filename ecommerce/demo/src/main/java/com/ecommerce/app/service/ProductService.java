package com.ecommerce.app.service;

import java.util.List;

public interface ProductService<T, R> {
    List<R> getAll();
    R getById(Long id);
    R create(T request);
    R update(Long id, T request);
    void delete(Long id);
}
