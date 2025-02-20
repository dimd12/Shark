package com.edumentor.dao;

import com.edumentor.models.Post;
import java.sql.Date;
import java.util.List;

/**
 * Interface for managing {@link Post} entities in the database.
 * Provides methods for saving, deleting, and retrieving posts based on various criteria,
 * such as user ID, category ID, title, and date range.
 * 
 * @author adrian
 */
public interface PostDaoIntf {

    /**
     * Saves a new post or updates an existing post in the database.
     *
     * @param post The {@link Post} object to be saved or updated.
     */
    void save(Post post);

    /**
     * Deletes a post from the database by its unique ID.
     *
     * @param postId The ID of the post to be deleted.
     */
    void delete(int postId);

    /**
     * Retrieves all posts from the database.
     *
     * @return A {@link List} of all {@link Post} objects.
     */
    List<Post> findAll();

    /**
     * Finds a specific post in the database by its unique ID.
     *
     * @param postId The ID of the post to be retrieved.
     * @return The {@link Post} object if found, or null if no post exists with the given ID.
     */
    Post findById(int postId);

    /**
     * Retrieves all posts created by a specific user.
     *
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects associated with the given user.
     */
    List<Post> findByUserId(int userId);

    /**
     * Retrieves all posts belonging to a specific category.
     *
     * @param categoryId The ID of the category whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects associated with the given category.
     */
    List<Post> findByCategoryId(int categoryId);

    /**
     * Finds posts that match a specific title or contain the title as part of their content.
     *
     * @param title The title (or part of it) to search for in posts.
     * @return A {@link List} of {@link Post} objects that match the given title.
     */
    List<Post> findByTitle(String title);

    /**
     * Retrieves posts created within a specific date range.
     *
     * @param dateCreated The creation date to filter posts by. 
     *                    This can represent a single date or be used in conjunction with other filters for a range.
     * @return A {@link List} of {@link Post} objects created within the specified date range.
     */
    List<Post> findByDateRange(Date dateCreated);

    /**
     * Uses the searchbar to search for a post.
     *
     * @param searchTerm The term entered in the text field.
     * @return The list of posts found.
     */
    List<Post> searchPosts(String searchTerm);
}
