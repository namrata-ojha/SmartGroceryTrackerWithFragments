package com.scu.smartgrocerytracker.constants;
import com.scu.smartgrocerytracker.*;

/**
 * Created by apar.sri on 2/19/2016.
 */
public enum Unit {

    POUNDS("lbs"),
    OUNCES("oz"),
    GALLON("gl"),
    KILOGRAM("kg"),
    GRAMs("g"),
    LITRE("l"),
    MILLILITRE("ml");

    String value;
    Unit(String value) {
        this.value = value;
    }

    public static Unit getUnit(String value) {
        for(Unit v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

  public String toString() {
        return value;
    }
}

