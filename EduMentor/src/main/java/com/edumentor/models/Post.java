package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a post made by a mentor in this application.
 * Each post has a poster, a title, a description, an url for the video, a timestamp and a category.
 * 
 * @author adrian
 */
public class Post {

    /** Unique identifier for the post (Primary Key). */
    int postId;
    /** 
     * The user who made the post (Foreign Key referencing User). 
     * @see User
     */
    User userId;
    /** The title for the post. */
    String title;
    /** The description for the post. */
    String description;
    /** The link to the video of the post. */
    String videoUrl;
    /** The date when the post was made. */
    Date dateCreated;
    /** 
     * The category asigned to the post (Foreign Key referencing Category). 
     * @see Category
     */
    Category categoryId;

    /** The default constructor. */
    public Post() {
    }

    /**
     * Parameterized constructor to initialize a Post object.
     * 
     * @param postId Unique identifier for the post.
     * @param userId  The poster of the post.
     * @param title The title of the post.
     * @param description The description for the post.
     * @param videoUrl The link to the video posted.
     * @param dateCreated  The timestamp of when the message was sent.
     * @param categoryId The category asigned for the post.
     * 
     */
    public Post(int postId, User userId, String title, String description, String videoUrl, Date dateCreated, Category categoryId) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.dateCreated = dateCreated;
        this.categoryId = categoryId;
    }

    /** @return The unique ID of the post. */
    public int getPostId() {
        return postId;
    }

    /** @param postId Sets the post ID. */
    public void setPostId(int postId) {
        this.postId = postId;
    }

    /** @return The poster of the post. */
    public User getUserId() {
        return userId;
    }

    /** @param userId Sets the poster's ID. */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /** @return The title of the post. */
    public String getTitle() {
        return title;
    }

    /** @param title Sets the post's title. */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /** @return The description of the post. */
    public String getDescription() {
        return description;
    }

    /** @param description Sets the post's description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return The link to the video. */
    public String getVideoUrl() {
        return videoUrl;
    }

    /** @param videoUrl Sets the video that was posted. */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /** @return The date when the post was made. */
    public Date getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated Sets the date in which the post was published. */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return The category which was asigned's ID. */
    public Category getCategoryId() {
        return categoryId;
    }

    /** @param categoryId Sets the category to the post */
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Computes a hash code for this post based on its attributes.
     * Used for hashing in collections.
     * 
     * @return Hash code of the post.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.postId;
        hash = 79 * hash + Objects.hashCode(this.userId);
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.videoUrl);
        hash = 79 * hash + Objects.hashCode(this.dateCreated);
        hash = 79 * hash + Objects.hashCode(this.categoryId);
        return hash;
    }

    /**
     * Checks if two Post objects are equal based on their attributes.
     * 
     * @param obj The object to compare with.
     * @return true if both posts are equal, false otherwise.
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
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.videoUrl, other.videoUrl)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.dateCreated, other.dateCreated)) {
            return false;
        }
        if (!Objects.equals(this.categoryId, other.categoryId)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the Post object.
     * 
     * @return A formatted string with post details.
     */
    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", userId=" + userId + ", title=" + title + ", description=" + description + ", videoUrl=" + videoUrl + ", dateCreated=" + dateCreated + ", categoryId=" + categoryId + '}';
    }

    

}
