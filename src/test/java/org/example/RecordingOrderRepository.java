package org.example;

public class RecordingOrderRepository implements OrderRepository {

    private int saveCount = 0;
    private Order lastSavedOrder = null;

    @Override
    public void save(Order order) {
        saveCount++;
        lastSavedOrder = order;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public Order getLastSavedOrder() {
        return lastSavedOrder;
    }
}
