package com.edumentor.models;

import java.util.Objects;

/**
 * Represents a category for the posts in the application.
 * Each category has a name.
 *
 * @author adima
 */
public class Category {

    int categoryId; /** Unique identifier for the category (Primary Key). */
    String categoryName; /** The name of the category. */

    /* Default constructor. */
    public Category() {
    }

    /**
     * Parameterized constructor to initialize a Category object.
     *
     * @param categoryId Unique identifier for the category.
     * @param categoryName The name of the category
     */
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    /**
     * Parameterized constructor to initialize a Category object.
     *
     * @param categoryId Unique identifier for the category.
     */
    public Category(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the unique ID of the category.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId sets the category ID.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the name of the category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName sets the name of the category.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Computes a hash code for this category based on its attributes. 
     * Used for hashing in collections.
     *
     * @return Hash code of the message.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.categoryId;
        hash = 47 * hash + Objects.hashCode(this.categoryName);
        return hash;
    }

    /**
     * Checks if two Category objects are equal based on their attributes.
     *
     * @param obj The object to compare with.
     * @return true if both categories are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.categoryId != other.categoryId) {
            return false;
        }
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the Category object.
     *
     * @return A formatted string with category details.
     */
    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + '}';
    }

}
