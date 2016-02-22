package com.scu.smartgrocerytracker.shoppingList;

/**
 * Created by Noel on 2/18/2016.
 */
public class ShoppingBagItems {
    //For future use
    //    private double itemSize;
//    private String itemUnits;
//    private Timestamp itemExpiration;
//    private int upc;
    private int id;
    private String itemName;
    private String itemPath;
    private String itemCategory;


    public ShoppingBagItems(String itemNameNew, String itemPathNew, String itemCategoryNew) {
        this.itemName = itemNameNew;
        this.itemPath = itemPathNew;
        this.itemCategory = itemCategoryNew;
    }

    public ShoppingBagItems(int id, String itemName, String itemPath, String itemCategory) {
        this.id = id;
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.itemCategory = itemCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPath() {
        return itemPath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }


}
