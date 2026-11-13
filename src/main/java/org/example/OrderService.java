package org.example;

import org.example.commission.CommissionStrategy;
import org.example.discount.DiscountStrategy;
import org.example.factory.CommissionStrategyFactory;
import org.example.factory.DefaultCommissionStrategyFactory;
import org.example.factory.DefaultDiscountStrategyFactory;
import org.example.factory.DiscountStrategyFactory;

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

    private final OrderRepository repository;
    private final OrderValidator validator;
    private final ReceiptPrinter receiptPrinter;
    private final CommissionStrategyFactory commissionFactory;
    private final DiscountStrategyFactory discountFactory;

    public double processOrder(Order order) {

        // --- валидация ---
        validator.validate(order);

        // --- расчёт скидки по типу клиента ---
        DiscountStrategy discountStrategy = discountFactory.createStrategy(order.getCustomerType());
        double discount = discountStrategy.calculateDiscount(order.getAmount());

        // --- расчёт комиссии по способу оплаты ---
        CommissionStrategy commissionStrategy = commissionFactory.createStrategy(order.getPaymentType());
        double commission = commissionStrategy.calculateCommission(order.getAmount());

        // --- итоговая сумма ---
        double finalAmount = order.getAmount() - discount + commission;

        // --- формирование чека ---
        receiptPrinter.print(order, discount, commission, finalAmount);

        // --- "сохранение" заказа  ---
        repository.save(order);

        return finalAmount;
    }

    public static void main(String[] args) {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository, new DefaultDiscountStrategyFactory(), new DefaultCommissionStrategyFactory(), new BasicOrderValidation(), new ConsoleReceiptPrinter());

        Order order1 = new Order("1", "VIP", "CARD", List.of("Laptop", "Mouse"), 1000.0);
        Order order2 = new Order("2", "REGULAR", "CASH", List.of("Book"), 20.0);

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }

    public OrderService(OrderRepository repository, DiscountStrategyFactory discountFactory, CommissionStrategyFactory commissionFactory,
                        OrderValidator validator, ReceiptPrinter receiptPrinter) {

        this.repository = repository;
        this.discountFactory = discountFactory;
        this.commissionFactory = commissionFactory;
        this.validator = validator;
        this.receiptPrinter = receiptPrinter;

    }
}


