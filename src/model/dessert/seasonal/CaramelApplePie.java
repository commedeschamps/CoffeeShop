package model.dessert.seasonal;

import model.dessert.Dessert;

public class CaramelApplePie implements Dessert {
    @Override
    public String getDescription() {
        return "Caramel Apple Pie";
    }

    @Override
    public double getBaseCost() {
        return 1800;
    }
}
