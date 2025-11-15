package model.meal;

import model.menu.MenuItem;

public class Sandwich implements Meal {
    @Override
    public String getDescription() {
        return "Sandwich";
    }
    @Override
    public double getBaseCost() {
        return 1290;
    }
}
