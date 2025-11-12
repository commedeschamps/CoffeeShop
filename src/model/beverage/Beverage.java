package model.beverage;

import model.menu.MenuItem;


public interface Beverage extends MenuItem {
    String getDescription();
    double getBaseCost();
}
