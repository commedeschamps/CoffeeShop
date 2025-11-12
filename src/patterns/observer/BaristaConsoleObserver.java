package patterns.observer;

import model.order.Order;
import model.order.OrderItem;
import model.order.OrderStatus;


public class BaristaConsoleObserver implements OrderObserver {

    @Override
    public void onOrderStatusChanged(Order order) {
        if (order.getStatus() == OrderStatus.NEW) {
            int drinks = order.getItems().stream()
                    .mapToInt(OrderItem::getQuantity)
                    .sum();
            System.out.println("[Barista] New order: " + drinks + " drinks.");
        } else if (order.getStatus() == OrderStatus.READY) {
            System.out.println("[Barista] Order is READY, please call customer.");
        }
    }
}
