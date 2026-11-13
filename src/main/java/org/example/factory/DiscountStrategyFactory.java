package org.example.factory;

import org.example.discount.DiscountStrategy;

public abstract class DiscountStrategyFactory {

    public abstract DiscountStrategy createStrategy(String customerType);
}