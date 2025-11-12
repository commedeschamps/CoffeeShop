package patterns.factory;

import model.dessert.Brownie;
import model.dessert.Cheesecake;
import model.dessert.Croissant;
import model.dessert.Muffin;
import model.menu.MenuItem;

public class DessertFactory implements MenuItemFactory {

    @Override
    public MenuItem createItem(String code) {
        return switch (code) {
            case "CHEESE"  -> new Cheesecake();
            case "BROWNIE" -> new Brownie();
            case "MUFFIN"  -> new Muffin();
            case "CROISSANT"  -> new Croissant();
            default -> throw new IllegalArgumentException("Unknown dessert code: " + code);
        };
    }
}
