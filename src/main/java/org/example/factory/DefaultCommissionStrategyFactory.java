package org.example.factory;

import org.example.commission.CardCommissionStrategy;
import org.example.commission.CashCommissionStrategy;
import org.example.commission.CommissionStrategy;
import org.example.commission.TransferCommissionStrategy;

public class DefaultCommissionStrategyFactory extends CommissionStrategyFactory {

    @Override
    public CommissionStrategy createStrategy(String paymentType) {

        if (paymentType.equals("CARD")) {
            return new CardCommissionStrategy();
        } else if (paymentType.equals("CASH")) {
            return new CashCommissionStrategy();
        } else if (paymentType.equals("TRANSFER")) {
            return new TransferCommissionStrategy();
        } else {
            throw new RuntimeException("Unknown payment type: " + paymentType);
        }

    }
}