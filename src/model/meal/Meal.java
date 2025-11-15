package model.meal;

import model.menu.MenuItem;

public interface Meal extends MenuItem {
    String getDescription();
    double getBaseCost();
}
