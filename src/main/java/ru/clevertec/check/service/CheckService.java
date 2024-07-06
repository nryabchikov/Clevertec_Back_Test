package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.repository.CheckRepository;

import java.io.IOException;
import java.util.Map;

public class CheckService {
    private final CheckRepository checkRepository;
    public CheckService(String productFilePath) throws IOException {
        this.checkRepository = new CheckRepository(productFilePath);
    }

    public Check generateCheck(Map<Integer, Integer> mapOfIdAndAmountOfProducts,
                               int numberOfDiscountCard, int discountPercentage, double balanceDebitCard) throws IOException {
        return checkRepository.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard);
    }
}