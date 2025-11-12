package patterns.factory;

import model.menu.MenuItem;

public interface MenuItemFactory {
    MenuItem createItem(String code);
}
