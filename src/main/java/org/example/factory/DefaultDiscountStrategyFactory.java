package org.example.factory;

import org.example.discount.DiscountStrategy;
import org.example.discount.PremiumDiscountStrategy;
import org.example.discount.RegularDiscountStrategy;
import org.example.discount.VipDiscountStrategy;

public class DefaultDiscountStrategyFactory extends DiscountStrategyFactory {

    @Override
    public DiscountStrategy createStrategy(String customerType) {

        if (customerType.equals("REGULAR")) {
            return new RegularDiscountStrategy();
        } else if (customerType.equals("VIP")) {
            return new VipDiscountStrategy();
        } else if (customerType.equals("PREMIUM")) {
            return new PremiumDiscountStrategy();
        } else {
            throw new RuntimeException("Unknown customer type: " + customerType);
        }

    }
}