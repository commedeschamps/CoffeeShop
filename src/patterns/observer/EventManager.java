package patterns.observer;

import model.order.Order;

import java.util.*;

public class EventManager {
    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String op : operations) {
            listeners.put(op, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        if (list == null) {
            list = new ArrayList<>();
            listeners.put(eventType, list);
        }
        list.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        if (list != null) {
            list.remove(listener);
        }
    }

    public void notify(String eventType, Order order) {
        List<EventListener> list = listeners.get(eventType);
        if (list == null) return;
        for (EventListener listener : new ArrayList<>(list)) {
            listener.update(eventType, order);
        }
    }
}

