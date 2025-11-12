package patterns.factory;

import model.meal.Lunchbox;
import model.meal.Salad;
import model.meal.Sandwich;
import model.menu.MenuItem;

public class MealFactory implements MenuItemFactory {

    @Override
    public MenuItem createItem(String code) {
        return switch (code) {
            case "LUNCHBOX" -> new Lunchbox();
            case "SALAD"    -> new Salad();
            case "SANDWICH" -> new Sandwich();
            default -> throw new IllegalArgumentException("Unknown meal code: " + code);
        };
    }
}