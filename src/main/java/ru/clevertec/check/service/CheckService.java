package ru.clevertec.check.service;

import ru.clevertec.check.model.Check;
import ru.clevertec.check.repository.CheckRepository;

import java.sql.SQLException;
import java.util.Map;

public class CheckService {
    private final CheckRepository checkRepository;
    public CheckService(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    public Check generateCheck(Map<Integer, Integer> mapOfIdAndAmountOfProducts,
                               int numberOfDiscountCard, int discountPercentage, double balanceDebitCard) throws SQLException {
        return checkRepository.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard);
    }
}