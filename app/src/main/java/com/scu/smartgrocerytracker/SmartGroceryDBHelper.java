package com.scu.smartgrocerytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.scu.smartgrocerytracker.categories.Category;
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.constants.Unit;
import com.scu.smartgrocerytracker.db_helper.PantryDbUtils;
import com.scu.smartgrocerytracker.items.Items;
import com.scu.smartgrocerytracker.location.Place;
import com.scu.smartgrocerytracker.pantry.PantryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SmartGroceryDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "smartgrocery";
    //implementing singleton pattern for dbhelper instance
    private static SmartGroceryDBHelper instance;
    SQLiteDatabase myDb;

    private SmartGroceryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SmartGroceryDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SmartGroceryDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.myDb = db;
        //Initialize category table
        db.execSQL(Constants.CATEGORY_TABLE_CREATE);
        initCategory(db);


        // For item
        db.execSQL(Constants.ITEM_TABLE_CREATE);
        insertIntoItem(db);


        //For shopping List
        db.execSQL(Constants.SHOPPING_ITEM_TABLE_CREATE);

        //Initialize pantry list
        db.execSQL(PantryDbUtils.PANTRY_TABLE_CREATE);

        //For Pref store
        db.execSQL(Constants.PREF_STORE_TABLE_CREATE);
    }

    public SQLiteDatabase getDB() {
        return myDb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.CATEGORY_TABLE_NAME);
        onCreate(db);
    }


    /**
     * This method initializes the category table
     */
    private void initCategory(SQLiteDatabase db) {
        Log.d(this.getClass().getSimpleName(), "Initializing Category table!");
        List<Category> categories = new ArrayList<>();
        Category vegetables = new Category(Constants.CATEGORY_VEGETABLES_NAME, "vegetables.jpg");
        Category fruits = new Category(Constants.CATEGORY_FRUITS_NAME, "fruits.jpg");
        Category diary = new Category(Constants.CATEGORY_DAIRY_NAME, "diary.jpg");
        categories.add(vegetables);
        categories.add(fruits);
        categories.add(diary);

        for (Category c : categories) {
            insert(db, c);
        }
    }

    private void insertIntoItem(SQLiteDatabase db) {
        List<Items> itemList = new ArrayList<>();
        Items asparagusItems = new Items("Asparagus", "asparagusdb.jpg", "Vegetables");
        Items basilItems = new Items("Basil", "basildb.jpg", "Vegetables");
        Items vegetablesItems = new Items("Beet", "beetdb.jpg", "Vegetables");
        Items boardBeanItems = new Items("Broad Bean", "broadbeandb.jpg", "Vegetables");
        Items broccoliItems = new Items("Beans", "beansdb.jpg", "Vegetables");
        Items BrusselsItems = new Items("Brussels", "brusselsdb.jpg", "Vegetables");
        Items CabbageiItems = new Items("Cabbage", "cabbagechinesedb.jpg", "Vegetables");
        Items cantaloupedbItems = new Items("Cantaloupe", "cantaloupedb.jpg", "Vegetables");
        Items carrotdbItems = new Items("Carrot", "carrotdb.jpg", "Vegetables");
        Items cauliflowerdbItems = new Items("Cauliflower", "cauliflowerdb.jpg", "Vegetables");
        Items celerydbItems = new Items("Celery", "celerydb.jpg", "Vegetables");
        Items cilantrodbItems = new Items("Cilantro", "cilantrodb.jpg", "Vegetables");
        Items corndbItems = new Items("Corn", "corndb.jpg", "Vegetables");
        Items cucpickleItems = new Items("Cucpickle", "cucpickledb.jpg", "Vegetables");
        Items cucumberdbItems = new Items("Cucumber", "cucumberdb.jpg", "Vegetables");
        Items eggplantdbItems = new Items("Eggplant", "eggplantdb.jpg", "Vegetables");
        Items garlicdbItems = new Items("Garlic", "garlicdb.jpg", "Vegetables");
        Items kaledbItems = new Items("Kale", "kaledb.jpg", "Vegetables");
        Items kohlrabidbItems = new Items("Kohlrabi", "kohlrabidb.jpg", "Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
//        Items vegetablesItems = new Items("Asparagus","asparagusdb.jpg","Vegetables");
        Items mangoItems = new Items("Mango", "mango.jpg", "Fruits");
        Items orangeItems = new Items("Orange", "orange.jpg", "Fruits");
        Items bananaItems = new Items("Banana", "banana.jpg", "Fruits");
        Items apricotItems = new Items("Apricot", "apricot.jpg", "Fruits");
        Items cherryItems = new Items("Cherry", "cherry.jpg", "Fruits");
        Items figsItems = new Items("Figs", "figs.jpg", "Fruits");
        Items kiwiItems = new Items("Kiwi", "kiwi.jpg", "Fruits");
        Items grapesItems = new Items("Grapes", "grapes.jpg", "Fruits");
        Items guavaWebItems = new Items("Guava", "guavaWeb.jpg", "Fruits");
        Items berriesItems = new Items("Berries", "berries.jpg", "Fruits");

        //Dairy Product
        Items milkItems = new Items("Milk", "milk2.jpg", "Dairy");
        Items cheeseItems = new Items("Cheese", "cheese.png", "Dairy");
        Items yogurtItems = new Items("Greek Yogurt", "greekyogurt.jpg", "Dairy");
        Items buttersItems = new Items("Butter", "butter.jpg", "Dairy");
        Items sourcreamItems = new Items("Sour Cream", "sourcream.jpg", "Dairy");
        Items tofuItems = new Items("Tofu", "tofu.jpg", "Dairy");
        Items eggsItems = new Items("Eggs", "eggs.jpg", "Dairy");


        itemList.add(asparagusItems);
        itemList.add(basilItems);
        itemList.add(vegetablesItems);
        itemList.add(boardBeanItems);
        itemList.add(broccoliItems);
        itemList.add(BrusselsItems);
        itemList.add(CabbageiItems);
        itemList.add(cantaloupedbItems);
        itemList.add(carrotdbItems);
        itemList.add(cauliflowerdbItems);
        itemList.add(celerydbItems);
        itemList.add(cilantrodbItems);
        itemList.add(corndbItems);
        itemList.add(cucpickleItems);
        itemList.add(cucumberdbItems);
        itemList.add(eggplantdbItems);
        itemList.add(garlicdbItems);
        itemList.add(kaledbItems);
        itemList.add(kohlrabidbItems);
        //adding fruits
        itemList.add(mangoItems);
        itemList.add(orangeItems);
        itemList.add(bananaItems);
        itemList.add(apricotItems);
        itemList.add(cherryItems);
        itemList.add(figsItems);
        itemList.add(kiwiItems);
        itemList.add(grapesItems);
        itemList.add(guavaWebItems);
        itemList.add(berriesItems);

        //adding Dairy
        itemList.add(milkItems);
        itemList.add(cheeseItems);
        itemList.add(yogurtItems);
        itemList.add(buttersItems);
        itemList.add(sourcreamItems);
        itemList.add(tofuItems);
        itemList.add(eggsItems);


        for (Items i : itemList) {
            insertItem(db, i);
        }
    }


    //insert rows in Categories table
    public void insert(SQLiteDatabase db, Category category) {
        ContentValues newValues = new ContentValues();
        newValues.put(Constants.CATEGORY_NAME_COLUMN, category.getCategoryName());
        newValues.put(Constants.CATEGORY_IMAGE_NAME_COLUMN, category.getImageName());
        db.insert(Constants.CATEGORY_TABLE_NAME, null, newValues);
    }


    //insert rows in Item table
    public void insertItem(SQLiteDatabase db, Items item) {
        ContentValues newValues = new ContentValues();
        newValues.put(Constants.ITEM_NAME_COLUMN, item.getItemName());
        newValues.put(Constants.ITEM_IMAGE_PATH_COLUMN, item.getItemPath());
        newValues.put(Constants.ITEM_CATEGORY_COLUMN, item.getItemCategory());
        db.insert(Constants.ITEM_TABLE_NAME, null, newValues);
    }

    //insert rows in items in shopping List table
    public void insertItemInShoppingList(String itemName, String categoryName, Integer quantity, Integer itemRefrenceId, String itemPathh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        //  newValues.put(Constants.ITEM_UPC_COLUMN, item.getUpc());
        newValues.put(Constants.SHOPPINGLIST_ITEM_NAME_COLUMN, itemName);
        newValues.put(Constants.SHOOPINGLIST_ITEM_CATEGORY_COLUMN, categoryName);

        newValues.put(Constants.SHOOPINGLIST_ITEM_QUANTITY_COLUMN, quantity);
        newValues.put(Constants.SHOPPINGLIST_ITEM_IMAGE_PATH, itemPathh);
        newValues.put(Constants.SHOOPINGLIST_ITEM_ID_REFRENCE_COLUMN, itemRefrenceId);
        db.insert(Constants.SHOPPINGLIST_TABLE_NAME, null, newValues);
    }

//insert items into Preferred stores tables
public void insertPrefStore(Place place) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues newValues = new ContentValues();
    newValues.put(Constants.PREF_STORE_NAME_COL, place.name);
    newValues.put(Constants.PREF_STORE_ADDR_COL, place.formatted_address);
    newValues.put(Constants.PREF_STORE_PHONE_COL, place.formatted_phone_number);
    newValues.put(Constants.PREF_STORE_LAT_COL, place.geometry.location.lat);
    newValues.put(Constants.PREF_STORE_LNG_COL, place.geometry.location.lng);
    db.insert(Constants.PREF_STORE_TABLE_NAME, null, newValues);
}

    //get all preferred store
    public List<Place> getAllPreferredStores() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.PREF_STORE_ID, Constants.PREF_STORE_NAME_COL, Constants.PREF_STORE_ADDR_COL,Constants.PREF_STORE_PHONE_COL,Constants.PREF_STORE_LAT_COL,Constants.PREF_STORE_LNG_COL};
        Cursor cursor = db.query(Constants.PREF_STORE_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Place> places = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String storeName = cursor.getString(1);
            String storeAddr = cursor.getString(2);
            String storePhoneNum = cursor.getString(3);
            double lat = cursor.getDouble(4);
            double lng = cursor.getDouble(5);
            Place place = new Place();
            place.name = storeName;
            place.formatted_address = storeAddr;
            place.formatted_phone_number = storePhoneNum;
            place.geometry.location.lat = lat;
            place.geometry.location.lng = lng;
            places.add(place);

        }
        return places;

    }



    //get all rows from database and return List<Category>

    public List<Category> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.ID_COLUMN, Constants.CATEGORY_NAME_COLUMN, Constants.CATEGORY_IMAGE_NAME_COLUMN};
        Cursor cursor = db.query(Constants.CATEGORY_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Category> categories = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String categoryName = cursor.getString(1);
            String imagePath = cursor.getString(2);
            Category category = new Category(id, categoryName, imagePath);
            categories.add(category);
            Log.d("Categories Listing", String.format("%s,%s,%s", id, categoryName, imagePath));
        }
        return categories;

    }

    // get category name by passing the id
    public List<Category> getCategoryName(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.ID_COLUMN, Constants.CATEGORY_NAME_COLUMN, Constants.CATEGORY_IMAGE_NAME_COLUMN};
        Cursor cursor = db.query(Constants.CATEGORY_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Category> categories = new ArrayList<>();
        while (cursor.moveToNext()) {
            //int id = cursor.getInt(0);
            String categoryName = cursor.getString(1);
            String imagePath = cursor.getString(2);
//            Category category = new Category(id,categoryName,imagePath);
//            categories.add(category);
            Log.d("Categories Listing", String.format("%s,%s,%s", id, categoryName, imagePath));
        }
        return categories;

    }

    //Get all the items
    public List<Items> getAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.ITEM_ID_COLUMN, Constants.ITEM_NAME_COLUMN, Constants.ITEM_IMAGE_PATH_COLUMN, Constants.ITEM_CATEGORY_COLUMN};
        Cursor cursor = db.query(Constants.ITEM_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Items> itemsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String imagePath = cursor.getString(2);
            String itemCategory = cursor.getString(3);
            Items item = new Items(id, itemName, imagePath, itemCategory);
            itemsList.add(item);
            Log.d("Item Listing", String.format("%s,%s,%s", id, itemName, imagePath, itemCategory));
        }
        return itemsList;

    }

    //get items by category
    public List<Items> getItemsByCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = Constants.ITEM_CATEGORY_COLUMN + "='" + category + "'";
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.ITEM_ID_COLUMN, Constants.ITEM_NAME_COLUMN, Constants.ITEM_IMAGE_PATH_COLUMN, Constants.ITEM_CATEGORY_COLUMN};
        Cursor cursor = db.query(Constants.ITEM_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Items> itemsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String imagePath = cursor.getString(2);
            String itemCategory = cursor.getString(3);
            Items item = new Items(id, itemName, imagePath, itemCategory);
            itemsList.add(item);
            Log.d("Item by category ", String.format("%s,%s,%s", id, itemName, imagePath, itemCategory));
        }
        return itemsList;

    }

    /**
     * Method to insert item to Pantry table
     *
     * @param item
     */
    public void addToPantry(PantryItem item) {
        Log.d(this.getClass().getSimpleName(), "Adding pantry item :" + item);
        SQLiteDatabase db;
        if (myDb == null) {
            myDb = getWritableDatabase();
        }
        db = myDb;
        PantryDbUtils.addToPantry(db, item);
    }


    /**
     * Method to update the pantry item
     *
     * @param pantryItem
     */
    public void updatePantry(PantryItem pantryItem) {
        Log.d(this.getClass().getSimpleName(), "Updating pantry item :" + pantryItem.getId() + ":" + pantryItem.getName());
        SQLiteDatabase db;
        if (myDb == null) {
            myDb = getWritableDatabase();
        }
        db = myDb;
        PantryDbUtils.updatePantry(db, pantryItem);
    }

    public void deletePantry(PantryItem item) {
        Log.d(this.getClass().getSimpleName(), "Deleting pantry item :" + item);
        SQLiteDatabase db;
        if (myDb == null) {
            myDb = getWritableDatabase();
        }
        db = myDb;
        PantryDbUtils.deletePantryItem(db, item);
    }

    public void deletePantryItems(Set<Integer> pantryItems) {
        Log.d(this.getClass().getSimpleName(), "Deleting pantry items :" + pantryItems);
        SQLiteDatabase db;
        if (myDb == null) {
            myDb = getWritableDatabase();
        }
        db = myDb;
        PantryDbUtils.deletePantryItems(db, pantryItems);
    }

    public List<Items> getAllItemsFromShoppingList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.SHOPPINGLIST_ID_COLUMN, Constants.SHOPPINGLIST_ITEM_NAME_COLUMN, Constants.SHOPPINGLIST_ITEM_IMAGE_PATH, Constants.SHOOPINGLIST_ITEM_CATEGORY_COLUMN, Constants.SHOOPINGLIST_ITEM_ID_REFRENCE_COLUMN};
        Cursor cursor = db.query(Constants.SHOPPINGLIST_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<Items> itemsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String imagePath = cursor.getString(2);
            String itemCategory = cursor.getString(3);
            int itemId = cursor.getInt(4);
            Items item = new Items(itemId, itemName, imagePath, itemCategory);
            itemsList.add(item);
            Log.d("Item Listing", String.format("%s,%s,%s", id, itemName, imagePath, itemCategory));
        }
        return itemsList;

    }

    /**
     * Gets all the categories
     *
     * @return list of <code>Category</code>
     */
    public List<PantryItem> getAllPantryItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {Constants.ID_COLUMN, Constants.ITEM_ID_COLUMN, Constants.ITEM_NAME_COLUMN, Constants.CATEGORY_NAME_COLUMN
                , PantryDbUtils.PRICE_COLUMN, PantryDbUtils.UNIT_COLUMN, PantryDbUtils.EXPIRY_DATE_COLUMN, PantryDbUtils.TOTAL_QUANTITY_COLUMN};
        Cursor cursor = db.query(PantryDbUtils.PANTRY_TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        List<PantryItem> pantryItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int itemId = cursor.getInt(1);
            String itemName = cursor.getString(2);
            String categoryName = cursor.getString(3);
            double itemPrice = cursor.getDouble(4);
            String unit = cursor.getString(5);
            long expDate = cursor.getLong(6);
            double quantity = cursor.getDouble(7);

            PantryItem pantryItem = new PantryItem();
            pantryItem.setId(id);
            pantryItem.setItemId(itemId);
            pantryItem.setName(itemName);
            pantryItem.setCategory(categoryName);
            pantryItem.setPrice(itemPrice);
            pantryItem.setUnit(Unit.getUnit(unit));
            pantryItem.setExpiryDate(expDate);
            pantryItem.setTotalQuantity(quantity);

            pantryItems.add(pantryItem);

        }
        return pantryItems;

    }


    public void deleteByNameFromShoppingList(String name) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + Constants.SHOPPINGLIST_TABLE_NAME + " WHERE " + Constants.SHOPPINGLIST_ITEM_NAME_COLUMN + "='" + name + "'");
     //code fix-Aparna
     //   db.close();

    }


}
