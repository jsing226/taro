package com.taro.sampleservice.services;

import com.taro.sampleservice.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Integer id);
    List<Product> findAll();

    Product save(Product product);

    boolean update(Product product);
}
