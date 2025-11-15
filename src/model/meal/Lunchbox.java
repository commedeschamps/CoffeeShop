package model.meal;

import model.menu.MenuItem;

public class Lunchbox implements Meal {
    @Override
    public String getDescription() {
        return "Lunchbox";
    }

    @Override
    public double getBaseCost() {
        return 1800;
    }
}
