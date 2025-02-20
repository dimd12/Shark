package com.edumentor.services;

import com.edumentor.models.Category;
import java.util.List;

/**
 * Service interface for managing {@link Category} entities.
 * Provides methods for saving, deleting, and retrieving categories.
 * This interface defines the contract for any implementation handling category-related operations.
 * 
 * @author adrian
 */
public interface CategoryServiceIntf {

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
