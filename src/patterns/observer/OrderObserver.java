package patterns.observer;

import model.order.Order;

public interface OrderObserver {
    void onOrderStatusChanged(Order order);
}
