package model.dessert;

import model.menu.MenuItem;

public class Cheesecake implements MenuItem {

    @Override
    public String getDescription() {
        return "Cheesecake";
    }

    @Override
    public double getBaseCost() {
        return 1200;
    }
}
