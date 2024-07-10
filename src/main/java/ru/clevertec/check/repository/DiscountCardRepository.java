package ru.clevertec.check.repository;

import ru.clevertec.check.model.DiscountCard;
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
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM discount_card");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                discountCards.put(resultSet.getInt("number"), resultSet.getInt("amount"));
            }
        }
        return discountCards;
    }

    public DiscountCard getDiscountCardById(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM discount_card WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DiscountCard(resultSet.getInt("id"), resultSet.getInt("number"), resultSet.getShort("amount"));
                }
            }
        }
        return null;
    }

    public void addDiscountCard(DiscountCard discountCard) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO discount_card (number, amount) VALUES (?, ?)")) {
            statement.setInt(1, discountCard.getNumber());
            statement.setShort(2, discountCard.getAmount());
            statement.executeUpdate();
        }
    }

    public void updateDiscountCard(DiscountCard discountCard) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE discount_card SET number = ?, amount = ? WHERE id = ?")) {
            statement.setInt(1, discountCard.getNumber());
            statement.setShort(2, discountCard.getAmount());
            statement.setInt(3, discountCard.getId());
            statement.executeUpdate();
        }
    }

    public void deleteDiscountCard(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM discount_card WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public int getDiscountPercentageByNumber(int cardNumber) throws SQLException {
        int discountPercentage = 0;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT amount FROM discount_card WHERE number = ?")) {
            statement.setInt(1, cardNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    discountPercentage = resultSet.getInt("amount");
                }
            }
        }
        return discountPercentage;
    }

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
