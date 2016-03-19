package com.scu.smartgrocerytracker.items;

import java.io.Serializable;

/**
 * Created by namrataojha on 2/17/16.
 */
public class Items implements Serializable {
    //For future use
    //    private double itemSize;
//    private String itemUnits;
//    private Timestamp itemExpiration;
//    private int upc;
    private int id;
    private String itemName;
    private String itemPath;
    private String itemCategory;


    public Items(String itemNameNew, String itemPathNew, String itemCategoryNew) {
        this.itemName = itemNameNew;
        this.itemPath = itemPathNew;
        this.itemCategory = itemCategoryNew;
    }



    public Items(int id, String itemName, String itemPath, String itemCategory) {
        this.id = id;
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.itemCategory = itemCategory;
    }

    public int getId() {
        return id;
    }


    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */
    @Override
    public String toString() {
        return super.toString();
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
