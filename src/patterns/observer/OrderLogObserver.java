package patterns.observer;

import model.order.Order;


public class OrderLogObserver implements EventListener {

    @Override
    public void update(String eventType, Order order) {
        System.out.println("[Log] Order status changed to: " + order.getStatus());
    }
}
