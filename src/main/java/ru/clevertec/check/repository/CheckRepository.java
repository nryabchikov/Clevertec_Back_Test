package ru.clevertec.check.repository;

import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.Item;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.util.MathRounder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckRepository {
    private final ProductService productService;

    public CheckRepository(ProductService productService) throws IOException {
        this.productService = productService;
        //this.productService = new ProductService();
    }

    public Check generateCheck(Map<Integer, Integer> mapOfIdAndAmountOfProduct, int numberOfDiscountCard,
                               int discountPercentage, double balanceDebitCard) throws SQLException {
        Check.CheckBuilder checkBuilder = new Check.CheckBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;
        List<Item> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : mapOfIdAndAmountOfProduct.entrySet()) {
            int productId = entry.getKey();
            int amount = entry.getValue();
            Product product = productService.getProductById(productId);
            double itemTotalPrice = MathRounder.roundNumberToTwoDigits(product.getPrice() * amount);
            double itemDiscount = calculateItemDiscount(product, amount, discountPercentage);
            items.add(new Item(product, amount, itemDiscount));
            totalPrice += itemTotalPrice;
            totalDiscount += itemDiscount;
        }

        checkBuilder.setTotalPrice(totalPrice);
        checkBuilder.setNumberOfDiscountCard(numberOfDiscountCard);
        checkBuilder.setDiscountPercentage(discountPercentage);
        checkBuilder.setTotalDiscount(totalDiscount);

        for (Item item : items) {
            checkBuilder.addItem(item);
        }
        return checkBuilder.build();
    }

    double calculateItemDiscount(Product product, int amount, int discountPercentage) {
        double discount;
        if (!product.isWholesale() || amount < 5) {
            discount = MathRounder.roundNumberToTwoDigits(product.getPrice() * amount * discountPercentage / 100);
        } else {
            discount = MathRounder.roundNumberToTwoDigits(product.getPrice() * amount / 10);
        }
        return discount;
    }
}

