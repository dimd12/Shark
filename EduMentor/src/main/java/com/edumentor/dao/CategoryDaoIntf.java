package com.edumentor.dao;

import com.edumentor.models.Category;
import java.util.List;

/**
 * Interface for performing CRUD operations on {@link Category} entities.
 * Provides methods to save, delete, retrieve all categories, and find a
 * category by its ID. This interface is meant to be implemented by classes that
 * interact with the database.
 *
 * @author adrian
 */
public interface CategoryDaoIntf {

    /**
     * Saves a new category or updates an existing category in the database.
     *
     * @param category The {@link Category} object to be saved or updated.
     */
    void save(Category category);

    /**
     * Deletes a category from the database based on its unique ID.
     *
     * @param categoryId The ID of the category to be deleted.
     */
    void delete(int categoryId);

    /**
     * Retrieves all categories from the database.
     *
     * @return A {@link List} of {@link Category} objects representing all
     * categories.
     */
    List<Category> findAll();

    /**
     * Finds a specific category in the database by its unique ID.
     *
     * @param categoryId The ID of the category to be retrieved.
     * @return The {@link Category} object if found, or null if no category
     * exists with the given ID.
     */
    Category findById(int categoryId);
}
