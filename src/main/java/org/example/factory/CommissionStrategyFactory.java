package org.example.factory;

import org.example.commission.CommissionStrategy;

public abstract class CommissionStrategyFactory {

    public abstract CommissionStrategy createStrategy(String paymentType);
}