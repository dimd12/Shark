package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents the user which can do multiple actions.
 * Each user has an username, a password, an email, a first name, a last name, 
 * a timestamp, a link to the profile picture, a bio and a role asigned.
 *
 * @author adrian
 */
public class User {
    
    /** Unique identifier for the user (Primary Key). */
    int userId;
    /** Unique name given to the user. */
    String username;
    /** Password for security used on login. */
    String password;
    /** Unique email set to be contacted on. */
    String email;
    /** First name of the user. */
    String firstName;
    /** Last name of the user. */
    String lastName;
    /** Date in which the user's account was made on. */
    Date dateCreated;
    /** Link for the user's */
    String profilePictureUrl;
    /** The bio which the user can have. */
    String bio;
    /** 
     * The role assigned to the user (Foreign Key references Role). 
     * @see Role
     */
    Role roleId;

    /** Default constructor. */
    public User() {
    }

    /**
     * Parameterized constructor to initialize a Review object.
     * 
     * @param userId Unique identifier for the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email of the user.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param dateCreated The timestamp of when the user's account was created.
     * @param profilePictureUrl The link to the user's profile picture.
     * @param bio The bio on the user's profile.
     * @param roleId The user's role.
     */
    public User(int userId, String username, String password, String email, String firstName, String lastName, Date dateCreated, String profilePictureUrl, String bio, Role roleId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.roleId = roleId;
    }

    /** @return The user's unique ID. */
    public int getUserId() {
        return userId;
    }

    /** @param userId Sets the user's ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return The user's username. */
    public String getUsername() {
        return username;
    }

    /** @param username Sets the user's username. */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /** @return The user's password. */
    public String getPassword() {
        return password;
    }

    /** @param password Set the user's password. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return The user's email. */
    public String getEmail() {
        return email;
    }

    /** @param email Set the user's email. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return The user's first name. */
    public String getFirstName() {
        return firstName;
    }

    /** @param firstName Sets the user's first name. */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return The user's last name. */
    public String getLastName() {
        return lastName;
    }

    /** @param lastName Set the user's last name. */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return The date in which the user was created. */
    public Date getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated Sets the date in which the user was created. */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return The profile picture url of the user. */
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    /** @param profilePictureUrl Sets the profile picture url. */
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    /** @return The bio of the user. */
    public String getBio() {
        return bio;
    }

    /** @param bio Sets the user's bio. */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /** @return The user's role. */
    public Role getRoleId() {
        return roleId;
    }

    /** @param roleId Sets the user's role. */
    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    /**
     * Computes a hash code for this user based on its attributes.
     * Used for hashing in collections.
     * 
     * @return Hash code of the message.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.userId;
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.password);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.firstName);
        hash = 29 * hash + Objects.hashCode(this.lastName);
        hash = 29 * hash + Objects.hashCode(this.dateCreated);
        hash = 29 * hash + Objects.hashCode(this.profilePictureUrl);
        hash = 29 * hash + Objects.hashCode(this.bio);
        hash = 29 * hash + Objects.hashCode(this.roleId);
        return hash;
    }

    /**
     * Checks if two User objects are equal based on their attributes.
     * 
     * @param obj The object to compare with.
     * @return true if both users are equal, false otherwise.
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
        final User other = (User) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.profilePictureUrl, other.profilePictureUrl)) {
            return false;
        }
        if (!Objects.equals(this.bio, other.bio)) {
            return false;
        }
        if (!Objects.equals(this.dateCreated, other.dateCreated)) {
            return false;
        }
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the User object.
     * 
     * @return A formatted string with user details.
     */
    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", dateCreated=" + dateCreated + ", profilePictureUrl=" + profilePictureUrl + ", bio=" + bio + ", roleId=" + roleId + '}';
    }

    
    
    
}
