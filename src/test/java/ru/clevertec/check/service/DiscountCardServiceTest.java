package ru.clevertec.check.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.clevertec.check.repository.DiscountCardRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DiscountCardServiceTest {

    private DiscountCardService discountCardService;
    private DiscountCardRepository discountCardRepository;

    @BeforeEach
    public void setUp() {
        discountCardRepository = Mockito.mock(DiscountCardRepository.class);
        discountCardService = new DiscountCardService(discountCardRepository);
    }

    @Test
    public void getAllDiscountCards() throws SQLException {
        Map<Integer, Integer> expectedDiscountCards = new HashMap<>();
        expectedDiscountCards.put(1111, 3);
        expectedDiscountCards.put(2222, 5);
        when(discountCardRepository.getAllDiscountCards()).thenReturn(expectedDiscountCards);
        Map<Integer, Integer> actualDiscountCards = discountCardService.getAllDiscountCards();
        assertEquals(expectedDiscountCards, actualDiscountCards);
        verify(discountCardRepository, times(1)).getAllDiscountCards();
    }

    @Test
    public void getAllDiscountCardsThrowsSQLException() throws SQLException {
        when(discountCardRepository.getAllDiscountCards()).thenThrow(new SQLException("Invalid credentials."));
        assertThrows(SQLException.class, () -> {
            discountCardService.getAllDiscountCards();
        });
        verify(discountCardRepository, times(1)).getAllDiscountCards();
    }
}
