package model.meal;

import model.menu.MenuItem;

public class Salad implements Meal {
    @Override
    public String getDescription() {
        return "Fresh salad";
    }

    @Override
    public double getBaseCost() {
        return 790;

    }
}
