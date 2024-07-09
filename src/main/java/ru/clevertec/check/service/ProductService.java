package ru.clevertec.check.service;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product getProductById(int id) throws SQLException {
        return productRepository.getProductById(id);
    }
}
