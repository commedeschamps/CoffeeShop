package patterns.observer;

import model.order.Order;

public interface EventListener {
    void update(String eventType, Order order);
}

