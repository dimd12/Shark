package com.edumentor.services.impl;

import com.edumentor.dao.UserDaoIntf;
import com.edumentor.dao.impl.UserDaoImpl;
import com.edumentor.models.User;
import com.edumentor.services.UserServiceIntf;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link UserServiceIntf} interface.
 * Provides methods for managing {@link User} entities, including saving, updating, deleting, 
 * and retrieving users by various criteria such as ID, username, or role.
 * 
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * It interacts with the {@link UserDaoIntf} to perform database operations.
 * 
 * @author adrian
 */
public class UserServiceImpl implements UserServiceIntf {

    // Reference to the DAO layer for user operations
    private final UserDaoIntf userDao = UserDaoImpl.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private UserServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link UserServiceImpl}.
     *
     * @return The singleton instance of {@link UserServiceImpl}.
     */
    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link User} to the database.
     *
     * @param user The {@link User} object to be saved.
     */
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    /**
     * Updates an existing {@link User}'s details in the database.
     *
     * @param user The {@link User} object containing updated information.
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * Deletes an existing {@link User} by their unique ID.
     *
     * @param userId The ID of the {@link User} to be deleted.
     */
    @Override
    public void delete(int userId) {
        userDao.delete(userId);
    }

    /**
     * Retrieves all {@link User} entities from the database.
     *
     * @return A {@link List} of all {@link User} objects.
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Finds a specific {@link User} by their unique ID.
     *
     * @param userId The ID of the {@link User} to be retrieved.
     * @return The {@link User} object if found, or null if no such ID exists.
     */
    @Override
    public User findById(int userId) {
        return userDao.findById(userId);
    }

    /**
     * Finds a specific {@link User} by their username.
     *
     * @param username The username of the target {@link User}.
     * @return The {@link User} object if found, or null if no such username exists.
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Retrieves all {@link User} entities associated with a specific role ID.
     *
     * @param roleId The ID of the role whose associated users are to be retrieved.
     * @return A {@link List} of {@link User} objects associated with the given role ID.
     */
    @Override
    public List<User> findByRole(int roleId) {
        return userDao.findByRole(roleId);
    }

    /**
     * Assigns a specific role to a given {@link User}.
     *
     * @param userId The ID of the target {@link User}.
     * @param roleId The ID of the role to assign to the target {@link User}.
     */
    @Override
    public void assignRoleToUser(int userId, int roleId) {
        userDao.assignRoleToUser(userId, roleId);
    }

    /**
     * Finds the users similar to the search.
     *
     * @param searchTerm The term that was searched in the search bar.
     * @return The list of users with a similar username, first name, last name to the search.
     */
    @Override
    public List<User> searchUsers(String searchTerm) {
        return userDao.searchUsers(searchTerm);
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link UserServiceImpl}.
     */
    private static class UserServiceImplHolder {

        private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }
}
