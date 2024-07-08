package ru.clevertec.check.service;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;


import java.sql.SQLException;
import java.util.Map;

public class DiscountCardService {
    private final DiscountCardRepository discountCardRepository;

    public DiscountCardService() {
        this.discountCardRepository = new DiscountCardRepository();
    }
    public Map<Integer, Integer> getAllDiscountCards() throws SQLException {
        return discountCardRepository.getAllDiscountCards();
    }
}

