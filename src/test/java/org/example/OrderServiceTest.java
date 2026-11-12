package org.example;

import java.util.List;

public class OrderServiceTest {

    public static void main(String[] args) {
        OrderService service = new OrderService();

        Order test1 = new Order("test-1", "VIP", "CARD", List.of("Laptop", "Mouse"), 1000.0);
        Order test2 = new Order("test-2", "PREMIUM", "TRANSFER", List.of("Book"), 1000.0);
        Order test3 = new Order("test-3", "REGULAR", "CASH", List.of("iPhone"), 1000.0);

        double processOrderTest1 = service.processOrder(test1);
        double processOrderTest2 = service.processOrder(test2);
        double processOrderTest3 = service.processOrder(test3);

        if (processOrderTest1 != 970.0) {
            throw new AssertionError("Обнаружено несоответствие итоговой стоимости первого заказа с ожидаемым\n" +
                    "Ожидалось: " + 970.0 + " | Получено: " + processOrderTest1);
        }

        if (processOrderTest2 != 910.0) {
            throw new AssertionError("Обнаружено несоответствие итоговой стоимости второго заказа с ожидаемым\n" +
                    "Ожидалось: " + 910.0 + " | Получено: " + processOrderTest2);
        }

        if (processOrderTest3 != 1000.0) {
            throw new AssertionError("Обнаружено несоответствие итоговой стоимости третьего заказа с ожидаемым\n" +
                    "Ожидалось: " + 1000.0 + " | Получено: " + processOrderTest3);
        }

        System.out.println("Все проверки успешно пройдены");
        System.out.println("Final Test amount 1: " + processOrderTest1);
        System.out.println("Final Test amount 2: " + processOrderTest2);
        System.out.println("Final Test amount 3: " + processOrderTest3);
    }
}

