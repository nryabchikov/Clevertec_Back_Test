package ru.clevertec.check.repository;

import ru.clevertec.check.mapper.ProductMapper;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.util.DatabaseConnection;

import java.sql.*;

public class ProductRepository {

    // Существующий метод
    public Product getProductById(int id) throws SQLException {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = ProductMapper.mapToProduct(resultSet);
                }
            }
        }
        return product;
    }

    public void addProduct(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO product (description, price, quantity_in_stock, wholesale_product) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, product.getDescription());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantityInStock());
            statement.setBoolean(4, product.isWholesale());
            statement.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE product SET description=?, price=?, quantity_in_stock=?, wholesale_product=? WHERE id=?")) {
            statement.setString(1, product.getDescription());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantityInStock());
            statement.setBoolean(4, product.isWholesale());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
