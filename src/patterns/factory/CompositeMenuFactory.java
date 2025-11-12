package patterns.factory;

import model.menu.MenuItem;

import java.util.List;

public class CompositeMenuFactory implements MenuItemFactory {

    private final List<MenuItemFactory> factories;

    public CompositeMenuFactory(List<MenuItemFactory> factories) {
        this.factories = List.copyOf(factories);
    }

    @Override
    public MenuItem createItem(String code) {
        IllegalArgumentException lastException = null;

        for (MenuItemFactory factory : factories) {
            try {
                return factory.createItem(code);
            } catch (IllegalArgumentException e) {
                lastException = e;
            }
        }

        throw new IllegalArgumentException("Unknown menu code: " + code, lastException);
    }
}
