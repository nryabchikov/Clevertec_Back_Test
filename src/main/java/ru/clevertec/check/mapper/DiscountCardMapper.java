package ru.clevertec.check.mapper;

import ru.clevertec.check.model.DiscountCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardMapper {
    public static DiscountCard mapToDiscountCard(ResultSet resultSet) throws SQLException {
        return new DiscountCard(
                resultSet.getInt("id"),
                resultSet.getInt("number"),
                resultSet.getShort("amount")
        );
    }
}
