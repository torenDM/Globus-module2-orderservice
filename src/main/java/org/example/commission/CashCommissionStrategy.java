package org.example.commission;

public class CashCommissionStrategy implements CommissionStrategy {

    @Override
    public double calculateCommission(double amount) {
        return 0.0;
    }

}
