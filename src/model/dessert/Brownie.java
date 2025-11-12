package model.dessert;

import model.menu.MenuItem;

public class Brownie implements MenuItem {

    @Override
    public String getDescription() {
        return "Brownie";
    }

    @Override
    public double getBaseCost() {
        return 1000;
    }
}
