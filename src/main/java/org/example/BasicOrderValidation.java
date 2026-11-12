package org.example;

public class BasicOrderValidation {

    public void validate(Order order) {

        // --- валидация ---
        if (order.getAmount() <= 0) {
            throw new RuntimeException("Order amount must be positive");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

    }
}
