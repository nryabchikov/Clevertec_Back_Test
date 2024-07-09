package ru.clevertec.check.repository;

import ru.clevertec.check.mapper.ProductMapper;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.util.DatabaseConnection;

import java.sql.*;

public class ProductRepository {
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

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
