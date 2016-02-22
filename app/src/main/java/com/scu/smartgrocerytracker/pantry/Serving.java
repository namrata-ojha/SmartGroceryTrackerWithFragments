package com.scu.smartgrocerytracker.pantry;

import com.scu.smartgrocerytracker.constants.ServingType;
import com.scu.smartgrocerytracker.constants.Unit;

/**
 * Class to hold 1 serving
 * e.g 1 serving of milk = 250ml cup where quantity = 250, unit = ml and size = cup
 *     1 serving of apple = 1 full
 *     1 serving of banana = 1 half (i.e half banana)
 */
public class Serving {
    //quantity of 1 serving
    private double quantity;

    //Unit of served quantity
    private Unit unit;

    //serving size(e.g cup,tpsp)
    private ServingType type;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ServingType getType() {
        return type;
    }

    public void setSize(ServingType type) {
        this.type = type;
    }
}
