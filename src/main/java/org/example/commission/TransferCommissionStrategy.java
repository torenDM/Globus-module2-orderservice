package org.example.commission;

public class TransferCommissionStrategy implements CommissionStrategy {

    @Override
    public double calculateCommission(double amount) {
        return amount*0.01;
    }

}
