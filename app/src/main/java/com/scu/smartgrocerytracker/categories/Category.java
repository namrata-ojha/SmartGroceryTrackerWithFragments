package com.scu.smartgrocerytracker.categories;

/**
 * Class that represents Category table
 */
public class Category {

    private String categoryName;
    private int id;
    private String imageName;

    public Category(String categoryName, String imageName) {
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    public Category(int id, String categoryName, String imageName) {
        this.categoryName = categoryName;
        this.imageName = imageName;
        this.id = id;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }
}
