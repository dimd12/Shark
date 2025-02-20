package com.edumentor.dao.impl;

import com.edumentor.dao.RoleDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link RoleDaoIntf} interface.
 * Provides methods for interacting with the database to perform CRUD operations on {@link Role} entities.
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * 
 * Handles operations such as adding, deleting, and retrieving roles by ID or name.
 * 
 * @author adrian
 */
public class RoleDaoImpl implements RoleDaoIntf {

    private static final Logger LOG = Logger.getLogger(RoleDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to get the singleton instance of this class.
     */
    private RoleDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link RoleDaoImpl}.
     *
     * @return The singleton instance of {@link RoleDaoImpl}.
     */
    public static RoleDaoImpl getInstance() {
        return RoleDaoImplHolder.INSTANCE;
    }

    /**
     * Adds a new role to the database. Throws an exception if the role name is null or empty.
     *
     * @param role The {@link Role} object to be added.
     */
    @Override
    public void add(Role role) {
        if (role == null || role.getRoleName() == null || role.getRoleName().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        String sql = "INSERT INTO roles (role_name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getRoleName());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error adding role: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a role from the database by its unique ID.
     *
     * @param roleId The ID of the role to be deleted.
     */
    @Override
    public void delete(int roleId) {
        String sql = "DELETE FROM roles WHERE role_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting role: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all roles from the database.
     *
     * @return A {@link List} of all {@link Role} objects in the database.
     */
    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        List<Role> roles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                roles.add(role);
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding roles: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return roles;
    }

    /**
     * Finds a specific role in the database by its unique ID.
     *
     * @param roleId The ID of the role to be retrieved.
     * @return The {@link Role} object if found, or null if no role exists with the given ID.
     */
    @Override
    public Role findById(int roleId) {
        String sql = "SELECT * FROM roles WHERE role_id = ?";
        Role role = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    role.setRoleId(rs.getInt("role_id"));
                    role.setRoleName(rs.getString("role_name"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding role by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return role;
    }

    /**
     * Finds a specific role in the database by its unique name.
     *
     * @param roleName The name of the role to be retrieved.
     * @return The {@link Role} object if found, or null if no role exists with the given name.
     */
    @Override
    public Role findByName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        Role role = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roleName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    role.setRoleId(rs.getInt("role_id"));
                    role.setRoleName(rs.getString("role_name"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding role by name: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return role;
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link RoleDaoImpl}.
     */
    private static class RoleDaoImplHolder {

        private static final RoleDaoImpl INSTANCE = new RoleDaoImpl();
    }
}
