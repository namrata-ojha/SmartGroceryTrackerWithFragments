package com.scu.smartgrocerytracker.db_helper;

import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.pantry.PantryItem;



import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * This class contains utility methods to do db operations on Pantry list
 */
public class PantryDbUtils {

    //Pantry list constants
    public static final String PANTRY_TABLE_NAME = "pantry_list";
    public static final String PRICE_COLUMN = "price";
    public static final String TOTAL_QUANTITY_COLUMN = "total_quantity";
    public static final String UNIT_COLUMN= "unit";
    public static final String EXPIRY_DATE_COLUMN = "expiry_date";
    public static final String QUANTITY_USED_COLUMN = "quantity_used";
    public static final String SERVING_QUANTITY_COLUMN = "serving_quantity";
    public static final String SERVING_UNIT_COLUMN = "serving_unit";
    public static final String SERVING_TYPE_COLUMN = "serving_type";


    public static final String PANTRY_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s text," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s integer," +
                    "  %s text," +
                    " FOREIGN KEY ("+ Constants.ITEM_ID_COLUMN+") REFERENCES "+Constants.ITEM_TABLE_NAME+"("+Constants.ITEM_ID_COLUMN+"));",
            PANTRY_TABLE_NAME,
            Constants.ID_COLUMN,
            Constants.ITEM_NAME_COLUMN,
            Constants.CATEGORY_NAME_COLUMN,
            Constants.ITEM_ID_COLUMN,
            PRICE_COLUMN,
            TOTAL_QUANTITY_COLUMN,
            UNIT_COLUMN,
            EXPIRY_DATE_COLUMN,
            QUANTITY_USED_COLUMN,
            SERVING_QUANTITY_COLUMN,
            SERVING_UNIT_COLUMN,
            SERVING_TYPE_COLUMN);


    /**
     * Method to insert Pantry item to Pantry table
     * @param db
     * @param item
     */
    public static void addToPantry(SQLiteDatabase db, PantryItem item) {
        ContentValues newValues = new ContentValues();
        newValues.put(Constants.ITEM_ID_COLUMN,item.getItemId());
        newValues.put(Constants.ITEM_NAME_COLUMN, item.getName());
        newValues.put(Constants.CATEGORY_NAME_COLUMN, item.getCategory());
        newValues.put(PRICE_COLUMN, item.getPrice());
        newValues.put(TOTAL_QUANTITY_COLUMN,item.getTotalQuantity());
        newValues.put(UNIT_COLUMN, item.getUnit().toString());
        newValues.put(EXPIRY_DATE_COLUMN, item.getExpiryDate());
        newValues.put(QUANTITY_USED_COLUMN, item.getQuantityUsed());
      /*  newValues.put(SERVING_QUANTITY_COLUMN, item.getServing().getQuantity());
        newValues.put(SERVING_UNIT_COLUMN, item.getServing().getUnit().toString());
        newValues.put(SERVING_TYPE_COLUMN, item.getServing().getType().toString());*/
        try {
            db.insert(PANTRY_TABLE_NAME, null, newValues);
        }
        catch(Exception e) {
            Log.d("PantryDbUtils", "Error inserting into pantry_list:" + e);
        }
        Log.d("PantryDbUtils", "After insert");
    }

    /**
     * Method to insert Pantry item to Pantry table
     * @param db
     * @param item
     */
    public static void updatePantry(SQLiteDatabase db, PantryItem item) {
        ContentValues newValues = new ContentValues();
        newValues.put(Constants.ITEM_ID_COLUMN,item.getItemId());
        newValues.put(Constants.ITEM_NAME_COLUMN, item.getName());
        newValues.put(Constants.CATEGORY_NAME_COLUMN, item.getCategory());
        newValues.put(PRICE_COLUMN, item.getPrice());
        newValues.put(TOTAL_QUANTITY_COLUMN,item.getTotalQuantity());
        newValues.put(UNIT_COLUMN, item.getUnit().toString());
        newValues.put(EXPIRY_DATE_COLUMN, item.getExpiryDate());
        newValues.put(QUANTITY_USED_COLUMN, item.getQuantityUsed());
      /*  newValues.put(SERVING_QUANTITY_COLUMN, item.getServing().getQuantity());
        newValues.put(SERVING_UNIT_COLUMN, item.getServing().getUnit().toString());
        newValues.put(SERVING_TYPE_COLUMN, item.getServing().getType().toString());*/
        try {
            db.update(PANTRY_TABLE_NAME, newValues, Constants.ID_COLUMN + "=" + item.getId(), null);
        }
        catch(Exception e) {
            Log.d("PantryDbUtils", "Error updating pantry item:" + item.getName() + " - " + e);
        }
        Log.d("PantryDbUtils", "Pantry item " + item.getName() + " updated with new values:"+newValues);
    }

    public static void deletePantryItem(SQLiteDatabase db,PantryItem item) {
        try {
            int result = db.delete(PANTRY_TABLE_NAME, Constants.ID_COLUMN + "=" + item.getId(), null);
            Log.d("DeletePantry", result + " row(s) affected while deleting item :" +  item.getId() + "-" + item.getName());
        }
        catch(SQLException e) {
            Log.e("DeletePantry", "Error deleting pantry item :" + item);
        }


    }

}
