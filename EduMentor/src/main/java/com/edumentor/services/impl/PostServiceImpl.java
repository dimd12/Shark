package com.edumentor.services.impl;

import com.edumentor.dao.PostDaoIntf;
import com.edumentor.dao.impl.PostDaoImpl;
import com.edumentor.models.Post;
import com.edumentor.services.PostServiceIntf;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link PostServiceIntf} interface.
 * Provides methods for managing {@link Post} entities, including saving, deleting, 
 * and retrieving posts by various criteria such as user ID, category ID, title, or date range.
 * 
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * It interacts with the {@link PostDaoIntf} to perform database operations.
 * 
 * @author adrian
 */
public class PostServiceImpl implements PostServiceIntf {

    // Reference to the DAO layer for post operations
    private final PostDaoIntf postDao = PostDaoImpl.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private PostServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link PostServiceImpl}.
     *
     * @return The singleton instance of {@link PostServiceImpl}.
     */
    public static PostServiceImpl getInstance() {
        return PostServiceImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Post} to the database.
     *
     * @param post The {@link Post} object to be saved.
     */
    @Override
    public void save(Post post) {
        postDao.save(post);
    }

    /**
     * Deletes an existing {@link Post} by its unique ID.
     *
     * @param postId The ID of the {@link Post} to be deleted.
     */
    @Override
    public void delete(int postId) {
        postDao.delete(postId);
    }

    /**
     * Retrieves all {@link Post} entities from the database.
     *
     * @return A {@link List} of all {@link Post} objects.
     */
    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    /**
     * Finds a specific {@link Post} by its unique ID.
     *
     * @param postId The ID of the {@link Post} to be retrieved.
     * @return The {@link Post} object if found, or null if no such ID exists.
     */
    @Override
    public Post findById(int postId) {
        return postDao.findById(postId);
    }

    /**
     * Retrieves all {@link Post} entities created by a specific user, identified by their user ID.
     *
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects created by the specified user.
     */
    @Override
    public List<Post> findByUserId(int userId) {
        return postDao.findByUserId(userId);
    }

    /**
     * Retrieves all {@link Post} entities within a specific category, identified by its category ID.
     *
     * @param categoryId The ID of the category whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects belonging to the specified category.
     */
    @Override
    public List<Post> findByCategoryId(int categoryId) {
        return postDao.findByCategoryId(categoryId);
    }

    /**
     * Finds all {@link Post} entities with a title matching the given string.
     *
     * @param title The title or part of the title to search for.
     * @return A {@link List} of {@link Post} objects with titles matching the given string.
     */
    @Override
    public List<Post> findByTitle(String title) {
        return postDao.findByTitle(title);
    }

    /**
     * Retrieves all {@link Post} entities created within a specific date range.
     *
     * @param dateCreated The date range to filter posts by their creation date.
     * @return A {@link List} of {@link Post} objects created within the specified date range.
     */
    @Override
    public List<Post> findByDateRange(Date dateCreated) {
        return postDao.findByDateRange(dateCreated);
    }

    /**
     * Uses the searchbar to search for a post.
     *
     * @param searchTerm The term entered in the text field.
     * @return The list of posts found.
     */
    @Override
    public List<Post> searchPosts(String searchTerm) {
        return postDao.searchPosts(searchTerm);
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link PostServiceImpl}.
     */
    private static class PostServiceImplHolder {

        private static final PostServiceImpl INSTANCE = new PostServiceImpl();
    }
}
