CREATE TABLE product
(
    id                BIGSERIAL PRIMARY KEY,
    description       VARCHAR(50),
    price             DECIMAL(10, 2),
    quantity_in_stock INTEGER,
    wholesale_product BOOLEAN
);

CREATE TABLE discount_card
(
    id     BIGSERIAL PRIMARY KEY,
    number INTEGER,
    amount SMALLINT
);

INSERT INTO product (description, price, quantity_in_stock, wholesale_product)
VALUES ('Milk', 1.07, 10, TRUE),
       ('Cream 400g', 2.71, 20, TRUE),
       ('Yogurt 400g', 2.10, 7, TRUE),
       ('Packed potatoes 1kg', 1.47, 30, FALSE),
       ('Packed cabbage 1kg', 1.19, 15, FALSE),
       ('Packed tomatoes 350g', 1.60, 50, FALSE),
       ('Packed apples 1kg', 2.78, 18, FALSE),
       ('Packed oranges 1kg', 3.20, 12, FALSE),
       ('Packed bananas 1kg', 1.10, 25, TRUE),
       ('Packed beef fillet 1kg', 12.80, 7, FALSE),
       ('Packed pork fillet 1kg', 8.52, 14, FALSE),
       ('Packed chicken breasts 1kgSour', 10.75, 18, FALSE),
       ('Baguette 360g', 1.30, 10, TRUE),
       ('Drinking water 1.5l', 0.80, 100, FALSE),
       ('Olive oil 500ml', 5.30, 16, FALSE),
       ('Sunflower oil 1l', 1.20, 12, FALSE),
       ('Chocolate Ritter sport 100g', 1.10, 50, TRUE),
       ('Paulaner 0.5l', 1.10, 100, FALSE),
       ('Whiskey Jim Beam 1l', 13.99, 30, FALSE),
       ('Whiskey Jack Daniels 1l', 17.19, 20, FALSE);

INSERT INTO discount_card (number, amount)
VALUES (1111, 3),
       (2222, 3),
       (3333, 3),
       (4444, 3);