package org.example;

public interface ReceiptPrinter {

    void print(Order order, double discount, double commission, double finalAmount);

}