package ru.clevertec.check.mapper;

import ru.clevertec.check.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public static Product mapToProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getInt("quantity_in_stock"),
                resultSet.getBoolean("wholesale_product"));
    }
}
