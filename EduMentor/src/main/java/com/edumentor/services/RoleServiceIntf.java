package com.edumentor.services;

import com.edumentor.models.Role;
import java.util.List;

/**
 * Service interface for managing {@link Role} entities.
 * Provides methods for adding, deleting, and retrieving roles,
 * as well as finding roles by their unique ID or name.
 *
 * @author adrian
 */
public interface RoleServiceIntf {

    /**
     * Adds a new role to the database.
     *
     * @param role The {@link Role} object to be added.
     */
    void add(Role role);

    /**
     * Deletes a role from the database by its unique ID.
     *
     * @param roleId The ID of the role to be deleted.
     */
    void delete(int roleId);

    /**
     * Retrieves all roles from the database.
     *
     * @return A {@link List} of all {@link Role} objects.
     */
    List<Role> findAll();

    /**
     * Finds a specific role in the database by its unique ID.
     *
     * @param roleId The ID of the role to be retrieved.
     * @return The {@link Role} object if found, or null if no role exists with the given ID.
     */
    Role findById(int roleId);

    /**
     * Finds a specific role in the database by its unique name.
     *
     * @param roleName The name of the role to be retrieved.
     * @return The {@link Role} object if found, or null if no role exists with the given name.
     */
    Role findByName(String roleName);
}
