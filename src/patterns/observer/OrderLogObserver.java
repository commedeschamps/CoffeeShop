package patterns.observer;

import model.order.Order;


public class OrderLogObserver implements OrderObserver {

    @Override
    public void onOrderStatusChanged(Order order) {
        System.out.println("[Log] Order status changed to: " + order.getStatus());
    }
}
