package org.example.commission;

public class CardCommissionStrategy implements CommissionStrategy {

    @Override
    public double calculateCommission(double amount) {
        return amount*0.02;
    }

}
