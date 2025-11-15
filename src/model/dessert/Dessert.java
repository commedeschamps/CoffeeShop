package model.dessert;

import model.menu.MenuItem;

public interface Dessert extends MenuItem {
    String getDescription();
    double getBaseCost();
}
