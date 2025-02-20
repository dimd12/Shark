package com.edumentor.services.impl;

import com.edumentor.dao.CategoryDaoIntf;
import com.edumentor.dao.impl.CategoryDaoImpl;
import com.edumentor.models.Category;
import com.edumentor.services.CategoryServiceIntf;
import java.util.List;

/**
 * Implementation of the {@link CategoryServiceIntf} interface.
 * Provides methods for managing {@link Category} entities, including saving, deleting, 
 * and retrieving categories from the database.
 * 
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * It interacts with the {@link CategoryDaoIntf} to perform database operations.
 * 
 * @author adrian
 */
public class CategoryServiceImpl implements CategoryServiceIntf {

    // Reference to the DAO layer for category operations
    private final CategoryDaoIntf categoryDao = CategoryDaoImpl.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private CategoryServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link CategoryServiceImpl}.
     *
     * @return The singleton instance of {@link CategoryServiceImpl}.
     */
    public static CategoryServiceImpl getInstance() {
        return CategoryServiceImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Category} to the database.
     *
     * @param category The {@link Category} object to be saved.
     */
    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    /**
     * Deletes an existing {@link Category} by its unique ID.
     *
     * @param categoryId The ID of the {@link Category} to be deleted.
     */
    @Override
    public void delete(int categoryId) {
        categoryDao.delete(categoryId);
    }

    /**
     * Retrieves all {@link Category} entities from the database.
     *
     * @return A {@link List} of all {@link Category} objects.
     */
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * Finds a specific {@link Category} by its unique ID.
     *
     * @param categoryId The ID of the {@link Category} to be retrieved.
     * @return The {@link Category} object if found, or null if no such ID exists.
     */
    @Override
    public Category findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link CategoryServiceImpl}.
     */
    private static class CategoryServiceImplHolder {

        private static final CategoryServiceImpl INSTANCE = new CategoryServiceImpl();
    }
}
