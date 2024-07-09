package ru.clevertec.check.service;

import ru.clevertec.check.repository.DiscountCardRepository;


import java.sql.SQLException;
import java.util.Map;

public class DiscountCardService {
    private final DiscountCardRepository discountCardRepository;

    public DiscountCardService(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }
    public Map<Integer, Integer> getAllDiscountCards() throws SQLException {
        return discountCardRepository.getAllDiscountCards();
    }
}

