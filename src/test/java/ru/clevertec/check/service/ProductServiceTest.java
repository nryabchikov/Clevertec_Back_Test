package ru.clevertec.check.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void getProductById() throws SQLException {
        int productId = 1;
        Product expectedProduct = new Product(productId, "Milk", 1.07, 10, true);
        when(productRepository.getProductById(productId)).thenReturn(expectedProduct);
        Product actualProduct = productService.getProductById(productId);
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository, times(1)).getProductById(productId);
    }

    @Test
    public void getProductByIdThrowsSQLException() throws SQLException {
        int productId = 1;
        when(productRepository.getProductById(productId)).thenThrow(new SQLException("Invalid credentials."));
        assertThrows(SQLException.class, () -> {
            productService.getProductById(productId);
        });
        verify(productRepository, times(1)).getProductById(productId);
    }
}
