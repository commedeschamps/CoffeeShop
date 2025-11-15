package model.dessert.seasonal;

import model.beverage.Beverage;
import model.dessert.Dessert;

public class CinnamonRoll implements Dessert {
    @Override
    public String getDescription() {
        return "Cinnamon Roll";
    }

    @Override
    public double getBaseCost() {
        return 900;
    }
}
