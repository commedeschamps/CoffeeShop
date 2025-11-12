package model.dessert;

import model.menu.MenuItem;

public class Muffin implements MenuItem {
    @Override
    public String getDescription() {
        return "Muffin";
    }

    @Override
    public double getBaseCost() {
        return 1200;
    }
}
