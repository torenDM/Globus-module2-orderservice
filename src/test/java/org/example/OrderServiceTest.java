package org.example;

import org.example.factory.DefaultCommissionStrategyFactory;
import org.example.factory.DefaultDiscountStrategyFactory;

import java.util.List;

public class OrderServiceTest {

    static void expectFailure(OrderService service, Order order, String expectedMessage) {

        try {
            service.processOrder(order);
            throw new AssertionError(
                    "Ожидалось исключение с сообщением: " + expectedMessage
                            + ", но заказ был обработан без ошибки");
        } catch (RuntimeException e) {
            if (!expectedMessage.equals(e.getMessage())) {
                throw new AssertionError("Сообщение исключения не совпало с ожидаемым\n" +
                        "Ожидалось: " + expectedMessage + " | Получено:" + e.getMessage());
            }
        }


    }

    public static void main(String[] args) {
        RecordingOrderRepository repository = new RecordingOrderRepository();
        OrderService service = new OrderService(repository, new DefaultDiscountStrategyFactory(), new DefaultCommissionStrategyFactory(), new BasicOrderValidation(), new ConsoleReceiptPrinter());
        
        Order test1 = new Order("test-1", "VIP", "CARD", List.of("Laptop", "Mouse"), 1000.0);
        Order test2 = new Order("test-2", "PREMIUM", "TRANSFER", List.of("Book"), 1000.0);
        Order test3 = new Order("test-3", "REGULAR", "CASH", List.of("iPhone"), 1000.0);
        Order test4 = new Order("test-4", "VIP", "CARD", List.of("Computer"), 0.0);
        Order test5 = new Order("test-5", "VIP", "CARD", List.of("Computer"), -100.0);
        Order test6 = new Order("test-6", "VIP", "CARD", List.of(), 1000.0);
        Order test7 = new Order("test-7", "VIP", "CARD", null, 1000.0);
        Order test8 = new Order("test-8", "VIP", "CARD", List.of(), 0.0);

        if (repository.getSaveCount() != 0 || repository.getLastSavedOrder() != null) {
            throw new AssertionError("Обнаружено несоответствие количества сохраненных заказов с ожидаемым, либо не совпадает последний заказ\n" +
                    "Ожидалось: " + 0 + "," + null + " | Получено: " + repository.getSaveCount() + "," + repository.getLastSavedOrder());
        }

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

        if (repository.getSaveCount() != 3 || repository.getLastSavedOrder() != test3) {
            throw new AssertionError("Обнаружено несоответствие количества сохраненных заказов с ожидаемым, либо не совпадает последний заказ\n" +
                    "Ожидалось: " + 3 + "," + test3 + " | Получено: " + repository.getSaveCount() + "," + repository.getLastSavedOrder());
        }

        expectFailure(service, test4, "Order amount must be positive");
        expectFailure(service, test5, "Order amount must be positive");
        expectFailure(service, test6, "Order must contain at least one item");
        expectFailure(service, test7, "Order must contain at least one item");
        expectFailure(service, test8 , "Order amount must be positive");

        if (repository.getSaveCount() != 3 || repository.getLastSavedOrder() != test3) {
            throw new AssertionError("Обнаружено несоответствие количества сохраненных заказов с ожидаемым, либо не совпадает последний заказ\n" +
                    "Ожидалось: " + 3 + "," + test3 + " | Получено: " + repository.getSaveCount() + "," + repository.getLastSavedOrder());
        }

        System.out.println("Все проверки успешно пройдены");
    }
}

