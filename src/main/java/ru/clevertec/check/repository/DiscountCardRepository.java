package ru.clevertec.check.repository;

import ru.clevertec.check.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DiscountCardRepository {
    public Map<Integer, Integer> getAllDiscountCards() throws SQLException {
        Map<Integer, Integer> discountCards = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM discount_card");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                discountCards.put(resultSet.getInt("number"), resultSet.getInt("amount"));
            }
        }
        return discountCards;
    }
}
