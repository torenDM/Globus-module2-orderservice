package org.example.discount;

public class RegularDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(double amount) {
        return 0.0;
    }

}