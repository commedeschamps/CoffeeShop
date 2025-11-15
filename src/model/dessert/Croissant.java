package model.dessert;

import model.menu.MenuItem;


public class Croissant implements Dessert {
    @Override
    public String getDescription() {
        return "Croissant";
    }

    @Override
    public double getBaseCost() {
        return 990;
    }
}
