package com.scu.smartgrocerytracker.constants;

/**
 * Created by apar.sri on 2/20/2016.
 */
public enum ServingType {
    CUP("cup"),
    TABLE_SPOON("tbsp"),
    TEA_SPOON("tsp"),
    FULL("full"), // (e.g full apple)
    HALF("half"); // (e.g half banana)

    String value;

    ServingType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
