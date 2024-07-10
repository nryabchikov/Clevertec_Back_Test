package ru.clevertec.check.util;

public class MathRounder {
    public static double roundNumberToTwoDigits(double number) {
        return Math.floor(number * 100) / 100.0;
    }
}
