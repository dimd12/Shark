package com.edumentor.models;

import java.util.Objects;

/**
 * Represents the role which a User can have.
 * Each role has a name.
 *
 * @author adrian
 */
public class Role {
    
    /** Unique identifier for the role (Primary Key). */
    int roleId;
    /** Name of the role. */
    String roleName;

    /** Default constructor. */
    public Role() {
    }

    /**
     * Parameterized constructor to initialize a Role object.
     * 
     * @param roleId Unique identifier for the role.
     * @param roleName Name for the role.
     */
    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    /**
     * Parameterized constructor to initialize a Role object.
     *
     * @param roleId Unique identifier for the role.
     */
    public Role(int roleId) {
        this.roleId = roleId;
    }

    /** @return The unique ID of the role. */
    public int getRoleId() {
        return roleId;
    }

    /** @param roleId Sets the ID to the role. */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /** @return The name of the role. */
    public String getRoleName() {
        return roleName;
    }

    /** @param roleName Sets the name of the role. */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Computes a hash code for this role based on its attributes.
     * Used for hashing in collections.
     * 
     * @return Hash code of the message.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.roleId;
        hash = 41 * hash + Objects.hashCode(this.roleName);
        return hash;
    }

    /**
     * Checks if two Role objects are equal based on their attributes.
     * 
     * @param obj The object to compare with.
     * @return true if both roles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (this.roleId != other.roleId) {
            return false;
        }
        if (!Objects.equals(this.roleName, other.roleName)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the Role object.
     * 
     * @return A formatted string with role details.
     */
    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", roleName=" + roleName + '}';
    }
    
}
