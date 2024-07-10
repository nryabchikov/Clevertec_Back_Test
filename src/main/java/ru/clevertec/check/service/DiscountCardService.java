package ru.clevertec.check.service;

import ru.clevertec.check.model.DiscountCard;
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

    public DiscountCard getDiscountCardById(int id) throws SQLException {
        return discountCardRepository.getDiscountCardById(id);
    }

    public void addDiscountCard(DiscountCard discountCard) throws SQLException {
        discountCardRepository.addDiscountCard(discountCard);
    }

    public void updateDiscountCard(DiscountCard discountCard) throws SQLException {
        discountCardRepository.updateDiscountCard(discountCard);
    }

    public void deleteDiscountCard(int id) throws SQLException {
        discountCardRepository.deleteDiscountCard(id);
    }

    public int getDiscountPercentageByNumber(int cardNumber) throws SQLException {
        return discountCardRepository.getDiscountPercentageByNumber(cardNumber);
    }
}

