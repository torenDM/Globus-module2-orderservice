package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 Контекст: Тебе достался в наследство код от предыдущего разработчика — сервис обработки заказов интернет-магазина OrderService.
 Он работает, тесты (если бы они были) прошли бы, заказчик доволен... пока не попросил добавить новый тип клиента или способ оплаты.
 Тут выясняется, что каждое такое "простое" изменение требует лезть в один и тот же метод на 80 строк, где всё завязано друг на друга,
 и есть риск что-то сломать в соседней логике.
 Твоя задача: не переписать сервис с нуля, а отрефакторить — сохранив то же поведение (те же скидки, та же комиссия, тот же чек)
 Опирайся на SOLID и паттерны проектирования
Lombok использовать нельзя, все писать руками.
 */
public class OrderService {


    private final List<Order> orders = new ArrayList<>();
    private final BasicOrderValidation validator = new BasicOrderValidation();

    public double processOrder(Order order) {

        validator.validate(order);

        // --- расчёт скидки по типу клиента ---
        double discount = 0.0;
        String customerType = order.getCustomerType();
        if (customerType.equals("REGULAR")) {
            discount = 0.0;
        } else if (customerType.equals("VIP")) {
            discount = order.getAmount() * 0.05;
        } else if (customerType.equals("PREMIUM")) {
            discount = order.getAmount() * 0.10;
        } else {
            // TODO: добавить новый тип клиента — дописать ещё один else if
            throw new RuntimeException("Unknown customer type: " + customerType);
        }

        // --- расчёт комиссии по способу оплаты ---
        double commission = 0.0;
        String paymentType = order.getPaymentType();
        if (paymentType.equals("CARD")) {
            commission = order.getAmount() * 0.02;
        } else if (paymentType.equals("CASH")) {
            commission = 0.0;
        } else if (paymentType.equals("TRANSFER")) {
            commission = order.getAmount() * 0.01;
        } else {
            // TODO: добавить новый способ оплаты — дописать ещё один else if
            throw new RuntimeException("Unknown payment type: " + paymentType);
        }

        // --- итоговая сумма ---
        double finalAmount = order.getAmount() - discount + commission;

        // --- формирование чека прямо тут ---
        System.out.println("=== RECEIPT ===");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Items: " + order.getItems());
        System.out.println("Base amount: " + order.getAmount());
        System.out.println("Discount: -" + discount);
        System.out.println("Commission: +" + commission);
        System.out.println("Final amount: " + finalAmount);
        System.out.println("================");

        // --- "сохранение" заказа  ---
        orders.add(order);

        return finalAmount;
    }

    public static void main(String[] args) {
        OrderService service = new OrderService();

        Order order1 = new Order("1", "VIP", "CARD", List.of("Laptop", "Mouse"), 1000.0);
        Order order2 = new Order("2", "REGULAR", "CASH", List.of("Book"), 20.0);

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }
}

