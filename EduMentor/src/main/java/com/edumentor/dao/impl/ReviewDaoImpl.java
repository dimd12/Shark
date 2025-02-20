package com.edumentor.dao.impl;

import com.edumentor.dao.ReviewDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Post;
import com.edumentor.models.Review;
import com.edumentor.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link ReviewDaoIntf} interface.
 * Provides methods for interacting with the database to perform CRUD operations on {@link Review} entities.
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * 
 * Handles operations such as saving, deleting, and retrieving reviews based on sender or receiver IDs.
 * 
 * @author adrian
 */
public class ReviewDaoImpl implements ReviewDaoIntf {

    private static final Logger LOG = Logger.getLogger(ReviewDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to get the singleton instance of this class.
     */
    private ReviewDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link ReviewDaoImpl}.
     *
     * @return The singleton instance of {@link ReviewDaoImpl}.
     */
    public static ReviewDaoImpl getInstance() {
        return ReviewDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new review or updates an existing review in the database.
     *
     * @param review The {@link Review} object to be saved or updated.
     */
    @Override
    public void save(Review review) {
        String sql = "INSERT INTO reviews (rating, user_id, post_id, review_message, date_sent) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, review.getRating());
            statement.setInt(2, review.getUserId().getUserId());
            statement.setInt(3, review.getPostId().getPostId());
            statement.setString(4, review.getReviewMessage());
            statement.setDate(5, review.getDateSent());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving new review: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a review from the database by its unique ID.
     *
     * @param reviewId The ID of the review to be deleted.
     */
    @Override
    public void delete(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, reviewId);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting review: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_REVIEW_QUERY =
            "SELECT * FROM reviews " +
                    "LEFT JOIN users ON reviews.user_id = users.user_id " +
                    "LEFT JOIN posts ON reviews.post_id = posts.post_id ";

    /**
     * Retrieves all reviews from the database.
     *
     * @return A {@link List} of all {@link Review} objects.
     */
    @Override
    public List<Review> findAll() {
        String sql = BASE_REVIEW_QUERY;
        List<Review> reviews = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)){
            while(rs.next()){
                reviews.add(mapResultSetToReview(rs));
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding all reviews: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return reviews;
    }

    /**
     * Finds a specific review in the database by its unique ID.
     *
     * @param reviewId The ID of the review to be retrieved.
     * @return The {@link Review} object if found, or null if no review exists with the given ID.
     */
    @Override
    public Review findById(int reviewId) {
        String sql = BASE_REVIEW_QUERY;
        Review review = null;
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, reviewId);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    review = mapResultSetToReview(rs);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding review by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return review;
    }

    /**
     * Retrieves all reviews sent by a specific user (review sender).
     *
     * @param userId The ID of the user who sent the reviews.
     * @return A {@link List} of {@link Review} objects associated with the given sender.
     */
    @Override
    public List<Review> findByUserId(int userId) {
        String sql = BASE_REVIEW_QUERY + "WHERE reviews.user_id = ?";
        List<Review> reviews = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    reviews.add(mapResultSetToReview(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding reviews by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return reviews;
    }

    /**
     * Retrieves all reviews received by a specific user (review receiver).
     *
     * @param postId The ID of the post who received the reviews.
     * @return A {@link List} of {@link Review} objects associated with the given receiver.
     */
    @Override
    public List<Review> findByPostId(int postId) {
        String sql = BASE_REVIEW_QUERY + "WHERE reviews.post_id = ?";
        List<Review> reviews = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, postId);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    reviews.add(mapResultSetToReview(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding reviews by post ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return reviews;
    }

    /**
     * Maps a {@link ResultSet} row to a {@link Review} object.
     *
     * @param rs The {@link ResultSet} to map from. It's expected that the ResultSet contains columns
     *           named "review_id", "rating", "user_id", "username", "post_id", "title", "review_message", and "date_sent".
     * @return A {@link Review} object populated with data from the {@link ResultSet}.
     * @throws SQLException if a database access error occurs or if a column label
     *                      cannot be found in the {@link ResultSet}.
     */
    private Review mapResultSetToReview(ResultSet rs) throws SQLException{
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setRating(rs.getInt("rating"));

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        review.setUserId(user);

        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setTitle(rs.getString("title"));
        review.setPostId(post);

        review.setReviewMessage(rs.getString("review_message"));
        review.setDateSent(rs.getDate("date_sent"));

        return review;
    }


    /**
     * Inner static class responsible for holding the singleton instance of {@link ReviewDaoImpl}.
     */
    private static class ReviewDaoImplHolder {

        private static final ReviewDaoImpl INSTANCE = new ReviewDaoImpl();
    }
}
