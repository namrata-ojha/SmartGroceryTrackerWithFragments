package com.scu.smartgrocerytracker.pantry;

import com.scu.smartgrocerytracker.constants.Unit;

import java.util.Date;

/**
 * Class to represent an item in Pantry
 */
public class PantryItem {
    private int itemId;
    private String name;
    private String category;
    private double price;
    private double totalQuantity;
    private Unit unit;
    private double quantityUsed;
    //Holds one serving(e.g 1 serving of MILK = 250ml cup)
    private Serving serving;
    private Date expiryDate;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Serving getServing() {
        return serving;
    }

    public void setServing(Serving serving) {
        this.serving = serving;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double  getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(double quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    @Override
    public String toString() {
        return "PantryItem{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", totalQuantity=" + totalQuantity +
                ", unit=" + unit +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
