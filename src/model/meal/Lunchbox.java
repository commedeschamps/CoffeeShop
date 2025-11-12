package model.meal;

import model.menu.MenuItem;

public class Lunchbox implements MenuItem {
    @Override
    public String getDescription() {
        return "Lunchbox";
    }

    @Override
    public double getBaseCost() {
        return 1800;
    }
}
