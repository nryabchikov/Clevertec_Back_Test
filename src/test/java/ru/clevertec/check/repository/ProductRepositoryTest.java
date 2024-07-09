package ru.clevertec.check.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.clevertec.check.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        productRepository = new ProductRepository() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };
    }

    @Test
    void getProductById() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("description")).thenReturn("Milk");
        when(resultSet.getDouble("price")).thenReturn(1.07);
        when(resultSet.getInt("quantity_in_stock")).thenReturn(10);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(true);
        Product product = productRepository.getProductById(1);
        assertEquals(1, product.getId());
        assertEquals("Milk", product.getDescription());
        assertEquals(1.07, product.getPrice());
        assertEquals(10, product.getQuantityInStock());
        assertTrue(product.isWholesale());
        verify(connection).prepareStatement("SELECT * FROM product WHERE id=?");
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }

    @Test
    void getProductByIdNotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        Product product = productRepository.getProductById(2);
        assertNull(product);
        verify(connection).prepareStatement("SELECT * FROM product WHERE id=?");
        verify(preparedStatement).setInt(1, 2);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }
}
