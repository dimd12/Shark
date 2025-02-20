package com.edumentor.dao.impl;

import com.edumentor.dao.UserDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Role;
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
 * Implementation of the {@link UserDaoIntf} interface. Provides methods for
 * interacting with the database to perform CRUD operations on {@link User}
 * entities. This class uses a singleton pattern to ensure a single instance is
 * used throughout the application.
 *
 * Handles operations such as saving, updating, deleting, and retrieving users,
 * as well as assigning roles to users and retrieving users by specific criteria
 * like role or username.
 *
 * @author adrian
 */
public class UserDaoImpl implements UserDaoIntf {

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation. Use {@link #getInstance()}
     * to get the singleton instance of this class.
     */
    private UserDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link UserDaoImpl}.
     *
     * @return The singleton instance of {@link UserDaoImpl}.
     */
    public static UserDaoImpl getInstance() {
        return UserDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new user to the database. Throws an exception if the username is
     * null or empty.
     *
     * @param user The {@link User} object to be saved.
     */
    @Override
    public void save(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User username cannot be null or empty");
        }

        String sql = "INSERT INTO USERS (username, password, email, first_name, last_name, profile_picture_url, bio, role_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getProfilePictureUrl());
            statement.setString(7, user.getBio());
            statement.setInt(8, user.getRoleId().getRoleId());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing user's details in the database.
     *
     * @param user The {@link User} object containing updated information.
     */
    @Override
    public void update(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User username cannot be null or empty");
        }

        String sql = "UPDATE users SET username = ?, password = ?, email = ?, first_name = ?, last_name = ?, "
                + "profile_picture_url = ?, bio = ?, role_id = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getProfilePictureUrl());
            statement.setString(7, user.getBio());
            statement.setInt(8, user.getRoleId().getRoleId());
            statement.setInt(9, user.getUserId());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error updating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a user from the database by their unique ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_USER_QUERY =
            "SELECT * FROM users " +
                    "LEFT JOIN roles ON users.role_id = roles.role_id ";

    /**
     * Retrieves all users from the database.
     *
     * @return A {@link List} of all {@link User} objects in the database.
     */
    @Override
    public List<User> findAll() {
        String sql = BASE_USER_QUERY;
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error retrieving users: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Finds a specific user in the database by their unique ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The {@link User} object if found, or null if no such ID exists.
     */
    @Override
    public User findById(int userId) {
        String sql = BASE_USER_QUERY + "WHERE user_id = ?";
        User user = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding user by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Finds a specific user in the database by their username.
     *
     * @param username The username of the target {@link User}.
     * @return The {@link User} object if found, or null if no such username
     * exists.
     */
    @Override
    public User findByUsername(String username) {
        String sql = BASE_USER_QUERY +
                "WHERE username = ?";
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                    System.out.println(user);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding user by username: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }


    /**
     * Retrieves all users associated with a specific role ID.
     *
     * @param roleId The ID of the role whose associated users are to be
     * retrieved.
     * @return A {@link List} of {@link User} objects associated with the given
     * role ID.
     */
    @Override
    public List<User> findByRole(int roleId) {
        String sql = BASE_USER_QUERY + "WHERE role_id = ?";
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding users by role: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Assigns a specific role to a given {@link User} in the database by
     * updating their `role_id`.
     *
     * @param userId The ID of the target {@link User}.
     * @param roleId The ID of the role to assign to the target {@link User}.
     */
    @Override
    public void assignRoleToUser(int userId, int roleId) {
        String sql = "UPDATE users SET role_id = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.setInt(2, userId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error assigning role to a specific: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the users similar to the search.
     *
     * @param searchTerm The term that was searched in the search bar.
     */
    @Override
    public List<User> searchUsers(String searchTerm) {
        String sql = "SELECT * FROM users WHERE username LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
        List<User> users = new ArrayList<>();
        User user = null;
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    user = mapResultSetToUser(rs);
                    users.add(user);
                }
            }
            connection.close();
        } catch (SQLException e){
            LOG.severe("Error searching users: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Maps a {@link ResultSet} row to a {@link User} object. Extracts user
     * details and role information from the result set and populates a
     * {@link User} instance.
     *
     * @param rs The {@link ResultSet} containing user data from the database.
     * @return A {@link User} object populated with data from the current row of
     * the {@link ResultSet}.
     * @throws SQLException If an SQL error occurs while accessing the
     * {@link ResultSet}.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setDateCreated(rs.getDate("date_created"));
        user.setProfilePictureUrl(rs.getString("profile_picture_url"));
        user.setBio(rs.getString("bio"));

        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));

        user.setRoleId(role);

        return user;
    }


    private static class UserDaoImplHolder {

        private static final UserDaoImpl INSTANCE = new UserDaoImpl();
    }
}
