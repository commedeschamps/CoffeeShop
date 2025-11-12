package model.order;

import patterns.observer.OrderObserver;
import patterns.observer.OrderSubject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order implements OrderSubject {

    private final List<OrderItem> items;
    private final List<OrderObserver> observers = new ArrayList<>();
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
        notifyObservers();
    }

    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(this);
        }
    }
}
