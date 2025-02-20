package com.edumentor.services.impl;

import com.edumentor.dao.RoleDaoIntf;
import com.edumentor.dao.impl.RoleDaoImpl;
import com.edumentor.models.Role;
import com.edumentor.services.RoleServiceIntf;
import java.util.List;

/**
 * Implementation of the {@link RoleServiceIntf} interface.
 * Provides methods for managing {@link Role} entities, including adding, deleting, 
 * and retrieving roles by various criteria such as ID or name.
 * 
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * It interacts with the {@link RoleDaoIntf} to perform database operations.
 * 
 * @author adrian
 */
public class RoleServiceImpl implements RoleServiceIntf {

    // Reference to the DAO layer for role operations
    private final RoleDaoIntf roleDao = RoleDaoImpl.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private RoleServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link RoleServiceImpl}.
     *
     * @return The singleton instance of {@link RoleServiceImpl}.
     */
    public static RoleServiceImpl getInstance() {
        return RoleServiceImplHolder.INSTANCE;
    }

    /**
     * Adds a new {@link Role} to the database.
     *
     * @param role The {@link Role} object to be added.
     */
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    /**
     * Deletes an existing {@link Role} by its unique ID.
     *
     * @param roleId The ID of the {@link Role} to be deleted.
     */
    @Override
    public void delete(int roleId) {
        roleDao.delete(roleId);
    }

    /**
     * Retrieves all {@link Role} entities from the database.
     *
     * @return A {@link List} of all {@link Role} objects.
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * Finds a specific {@link Role} by its unique ID.
     *
     * @param roleId The ID of the {@link Role} to be retrieved.
     * @return The {@link Role} object if found, or null if no such ID exists.
     */
    @Override
    public Role findById(int roleId) {
        return roleDao.findById(roleId);
    }

    /**
     * Finds a specific {@link Role} by its name.
     *
     * @param roleName The name of the {@link Role} to be retrieved.
     * @return The {@link Role} object if found, or null if no such name exists.
     */
    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link RoleServiceImpl}.
     */
    private static class RoleServiceImplHolder {

        private static final RoleServiceImpl INSTANCE = new RoleServiceImpl();
    }
}
