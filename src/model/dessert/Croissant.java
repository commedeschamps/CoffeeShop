package model.dessert;

import model.menu.MenuItem;


public class Croissant implements MenuItem {
    @Override
    public String getDescription() {
        return "Croissant";
    }

    @Override
    public double getBaseCost() {
        return 990;
    }
}
