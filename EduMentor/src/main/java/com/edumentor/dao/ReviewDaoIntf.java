package com.edumentor.dao;

import com.edumentor.models.Review;
import java.util.List;

/**
 * Interface for managing {@link Review} entities in the database.
 * Provides methods for saving, deleting, and retrieving reviews based on various criteria,
 * such as sender ID and receiver ID.
 * 
 * @author adrian
 */
public interface ReviewDaoIntf {

    /**
     * Saves a new review or updates an existing review in the database.
     *
     * @param review The {@link Review} object to be saved or updated.
     */
    void save(Review review);

    /**
     * Deletes a review from the database by its unique ID.
     *
     * @param reviewId The ID of the review to be deleted.
     */
    void delete(int reviewId);

    /**
     * Retrieves all reviews from the database.
     *
     * @return A {@link List} of all {@link Review} objects.
     */
    List<Review> findAll();

    /**
     * Finds a specific review in the database by its unique ID.
     *
     * @param reviewId The ID of the review to be retrieved.
     * @return The {@link Review} object if found, or null if no review exists with the given ID.
     */
    Review findById(int reviewId);

    /**
     * Retrieves all reviews sent by a specific user (review sender).
     *
     * @param userId The ID of the user who sent the reviews.
     * @return A {@link List} of {@link Review} objects associated with the given sender.
     */
    List<Review> findByUserId(int userId);

    /**
     * Retrieves all reviews received by a specific user (review receiver).
     *
     * @param postId The ID of the post who received the reviews.
     * @return A {@link List} of {@link Review} objects associated with the given receiver.
     */
    List<Review> findByPostId(int postId);
}
