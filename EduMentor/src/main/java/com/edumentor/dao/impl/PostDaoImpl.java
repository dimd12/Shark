package com.edumentor.dao.impl;

import com.edumentor.dao.PostDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Category;
import com.edumentor.models.Post;
import com.edumentor.models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link PostDaoIntf} interface.
 * Provides methods for interacting with the database to perform CRUD operations on {@link Post} entities.
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * 
 * Handles operations such as saving, deleting, and retrieving posts based on user ID, category ID, title, or date range.
 * 
 * @author adrian
 */
public class PostDaoImpl implements PostDaoIntf {

    private static final Logger LOG = Logger.getLogger(PostDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to get the singleton instance of this class.
     */
    private PostDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link PostDaoImpl}.
     *
     * @return The singleton instance of {@link PostDaoImpl}.
     */
    public static PostDaoImpl getInstance() {
        return PostDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new post to the database. Throws an exception if the post title is null or empty.
     *
     * @param post The {@link Post} object to be saved.
     */
    @Override
    public void save(Post post) {
        if (post == null || post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Post title cannot be null or empty");
        }

        String sql = "INSERT INTO posts (user_id, title, description, video_url, date_created, category_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, post.getUserId().getUserId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getDescription());
            statement.setString(4, post.getVideoUrl());
            statement.setDate(5, post.getDateCreated());
            statement.setInt(6, post.getCategoryId().getCategoryId());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving post: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a post from the database by its unique ID.
     *
     * @param postId The ID of the post to be deleted.
     */
    @Override
    public void delete(int postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting post: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_POST_QUERY =
            "SELECT * FROM posts " +
                    "LEFT JOIN users ON posts.user_id = users.user_id " +
                    "LEFT JOIN categories ON posts.category_id = categories.category_id ";

    /**
     * Retrieves all posts from the database.
     *
     * @return A {@link List} of all {@link Post} objects in the database.
     */
    @Override
    public List<Post> findAll() {
        String sql = BASE_POST_QUERY;
        List<Post> posts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding posts: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }

    /**
     * Finds a specific post in the database by its unique ID.
     *
     * @param postId The ID of the post to be retrieved.
     * @return The {@link Post} object if found, or null if no post exists with the given ID.
     */
    @Override
    public Post findById(int postId) {
        String sql = BASE_POST_QUERY +
                "WHERE post_id = ?";
        Post post = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    post = mapResultSetToPost(rs);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding post by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return post;
    }

    /**
     * Retrieves all posts created by a specific user.
     *
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects associated with the given user.
     */
    @Override
    public List<Post> findByUserId(int userId) {
        String sql = BASE_POST_QUERY +
                "WHERE posts.user_id = ?";
        List<Post> posts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding posts by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }

    /**
     * Retrieves all posts belonging to a specific category.
     *
     * @param categoryId The ID of the category whose posts are to be retrieved.
     * @return A {@link List} of {@link Post} objects associated with the given category.
     */
    @Override
    public List<Post> findByCategoryId(int categoryId) {
        String sql = BASE_POST_QUERY +
                "WHERE posts.category_id = ?";
        List<Post> posts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding posts by categoryID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }

    /**
     * Finds posts that match a specific title or contain it as part of their content.
     *
     * @param title The title or part of it to search for in posts.
     * @return A {@link List} of {@link Post} objects that match the given title.
     */
    @Override
    public List<Post> findByTitle(String title) {
        String sql = BASE_POST_QUERY +
                "WHERE title LIKE ?";
        List<Post> posts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding posts by title: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }

    /**
     * Retrieves all posts created on a specific date.
     *
     * @param dateCreated The creation date to filter posts by.
     * @return A {@link List} of {@link Post} objects created on the specified date.
     */
    @Override
    public List<Post> findByDateRange(Date dateCreated) {
        String sql = BASE_POST_QUERY +
                "WHERE date_created = ?";
        List<Post> posts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, dateCreated);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding posts by date: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }

    /**
     * Uses the searchbar to search for a post.
     *
     * @param searchTerm The term entered in the text field.
     * @return The list of posts found.
     */
    @Override
    public List<Post> searchPosts(String searchTerm) {
        String sql = BASE_POST_QUERY +
                "WHERE title LIKE ?";
        List<Post> posts = new ArrayList<>();
        Post post = null;
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    post = mapResultSetToPost(rs);
                    posts.add(post);
                }
            }
            connection.close();
        } catch (SQLException e){
            LOG.severe("Error searching posts: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return posts;
    }


    /**
     * Maps a {@link ResultSet} row to a {@link Post} object.
     *
     * @param rs The {@link ResultSet} containing data from a query result row.
     * @return A {@link Post} object populated with data from the current row of the ResultSet.
     */
    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        
        // Populate fields from ResultSet into a Post object
        post.setPostId(rs.getInt("post_id"));

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        post.setUserId(user);

        post.setTitle(rs.getString("title"));
        post.setDescription(rs.getString("description"));
        post.setVideoUrl(rs.getString("video_url"));
        post.setDateCreated(rs.getDate("date_created"));

        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        post.setCategoryId(category);

        return post;
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link PostDaoImpl}.
     */
    private static class PostDaoImplHolder {

        private static final PostDaoImpl INSTANCE = new PostDaoImpl();
    }
}
