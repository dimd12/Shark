package com.edumentor.dao.impl;

import com.edumentor.dao.CategoryDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link CategoryDaoIntf} interface.
 * Provides methods for interacting with the database to perform CRUD operations on {@link Category} entities.
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * 
 * @author adrian
 */
public class CategoryDaoImpl implements CategoryDaoIntf {

    private static final Logger LOG = Logger.getLogger(CategoryDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to get the singleton instance of this class.
     */
    private CategoryDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link CategoryDaoImpl}.
     *
     * @return The singleton instance of {@link CategoryDaoImpl}.
     */
    public static CategoryDaoImpl getInstance() {
        return CategoryDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new category to the database. Throws an exception if the category name is null or empty.
     *
     * @param category The {@link Category} object to be saved.
     */
    @Override
    public void save(Category category) {
        if (category == null || category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }

        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getCategoryName());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving new category: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a category from the database by its unique ID.
     *
     * @param categoryId The ID of the category to be deleted.
     */
    @Override
    public void delete(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting category: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return A {@link List} of all {@link Category} objects in the database.
     */
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categories.add(category);
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding all categories: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return categories;
    }

    /**
     * Finds a specific category in the database by its unique ID.
     *
     * @param categoryId The ID of the category to be retrieved.
     * @return The {@link Category} object if found, or null if no category exists with the given ID.
     */
    @Override
    public Category findById(int categoryId) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        Category category = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding category by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return category;
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link CategoryDaoImpl}.
     */
    private static class CategoryDaoImplHolder {
        private static final CategoryDaoImpl INSTANCE = new CategoryDaoImpl();
    }
}
