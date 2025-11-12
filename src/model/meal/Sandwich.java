package model.meal;

import model.menu.MenuItem;

public class Sandwich implements MenuItem {
    @Override
    public String getDescription() {
        return "Sandwich";
    }
    @Override
    public double getBaseCost() {
        return 1290;
    }
}
