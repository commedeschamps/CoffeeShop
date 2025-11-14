package model.order;

import patterns.observer.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    public static final String EVENT_STATUS_CHANGED = "order:status_changed";

    private final List<OrderItem> items;
    private final EventManager events = new EventManager(EVENT_STATUS_CHANGED);
    private OrderStatus status;

    public Order(List<OrderItem> items) {
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.NEW;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
        events.notify(EVENT_STATUS_CHANGED, this);
    }

    public EventManager getEvents() {
        return events;
    }
}