package com.scu.smartgrocerytracker.constants;

public class Constants {
    //General constants
    //General constants
    public static final String CATEGORY_NAME = "category_name";
    public static final String IMAGE_NAME = "IMAGE_NAME";
    public static final String CATEGORY_VEGETABLES_NAME = "Vegetables";
    public static final String CATEGORY_FRUITS_NAME = "Fruits";
    public static final String CATEGORY_DAIRY_NAME = "Dairy";

    //Settings constants
    public static final String EXPIRY_NOTIFICATION_DAYS_PROPERTY = "edu.scu.smartgrocerytracker.expiryNotificationDays";


    //Database table constants
    public static final String ID_COLUMN = "_id";
    public static final String CATEGORY_NAME_COLUMN = "category_name";
    public static final String CATEGORY_IMAGE_NAME_COLUMN = "image_name";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text)",
            Constants.CATEGORY_TABLE_NAME, Constants.ID_COLUMN, Constants.CATEGORY_NAME_COLUMN, Constants.CATEGORY_IMAGE_NAME_COLUMN);

    //item table constants
    public static final String ITEM_TABLE_NAME = "items";
    public static final String ITEM_ID_COLUMN = "ITEM_id";
    public static final String ITEM_NAME_COLUMN = "item_name";
    public static final String ITEM_IMAGE_PATH_COLUMN = "image_path";
    public static final String ITEM_CATEGORY_COLUMN = "item_category";
    //public static final String ITEM_SIZE_COLUMN = "size";
    //public static final String ITEM_UNITS_COLUMN = "units";
    //public static final String ITEM_EXPIRATION_COLUMN = "expiration_date";
    //public static final String ITEM_UPC_COLUMN = "upc";


    //Item table

    public static final String ITEM_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s text REFERENCES %s(%s) on UPDATE CASCADE);",
            Constants.ITEM_TABLE_NAME, Constants.ITEM_ID_COLUMN, Constants.ITEM_NAME_COLUMN,
            Constants.ITEM_IMAGE_PATH_COLUMN, Constants.ITEM_CATEGORY_COLUMN,
            Constants.CATEGORY_TABLE_NAME, Constants.ID_COLUMN);


    //ShoppingList table constants
    //ShoppingList table constants
    public static final String SHOPPINGLIST_TABLE_NAME = "shopping_list";
    public static final String SHOPPINGLIST_ID_COLUMN = "ITEM_id";
    public static final String SHOPPINGLIST_ITEM_NAME_COLUMN = "shopping_item_name";
    public static final String SHOOPINGLIST_ITEM_CATEGORY_COLUMN = "shopping_item_category";
    public static final String SHOOPINGLIST_ITEM_QUANTITY_COLUMN = "shopping_item_quantity";
    public static final String SHOPPINGLIST_ITEM_IMAGE_PATH = "image_path";
    public static final String SHOOPINGLIST_ITEM_ID_REFRENCE_COLUMN = "shopping_item_id_reference";

    //Shopping List table

    public static final String SHOPPING_ITEM_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s text," +
                    " FOREIGN KEY ( %s)  REFERENCES %s(%s));",
            Constants.SHOPPINGLIST_TABLE_NAME, Constants.SHOPPINGLIST_ID_COLUMN, Constants.SHOPPINGLIST_ITEM_NAME_COLUMN,
            Constants.SHOOPINGLIST_ITEM_CATEGORY_COLUMN, Constants.SHOOPINGLIST_ITEM_QUANTITY_COLUMN,
            Constants.SHOOPINGLIST_ITEM_ID_REFRENCE_COLUMN, Constants.SHOPPINGLIST_ITEM_IMAGE_PATH, Constants.SHOOPINGLIST_ITEM_ID_REFRENCE_COLUMN,
            Constants.ITEM_TABLE_NAME, Constants.ITEM_ID_COLUMN);


}
