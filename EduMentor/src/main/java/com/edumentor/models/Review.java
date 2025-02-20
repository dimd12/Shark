package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a review made by a mentee towards the mentor in this application.
 * Each post has a rating, a sender, a receiver, a message, and a timestamp.
 * 
 * @author adrian
 */
public class Review {
    
    /** Unique identifier for the rating (Primary Key). */
    int reviewId;
    /** Number asigned from 1-5 as a rating. */
    int rating;
    /** 
     * The user who sent the review (Foreign Key references User). 
     * @see User
     */
    User userId;
    /**
     * The post for which the review was sent to (Foreign Key references Post).
     * @see Post
     */
    Post postId;
    /** Short text characterizing the performance */
    String reviewMessage;
    /** The date in which the review was sent */
    Date dateSent;

    /** Default constructor. */
    public Review() {
    }

    /**
     * Parameterized constructor to initialize a Review object.
     * 
     * @param reviewId Unique identifier for the review.
     * @param rating Number 1-5 on the performance.
     * @param userId  The sender of the review.
     * @param reviewMessage  The review content.
     * @param dateSent The timestamp of when the review was sent.
     */
    public Review(int reviewId, int rating, User userId, Post postId, String reviewMessage, Date dateSent) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.userId = userId;
        this.postId = postId;
        this.reviewMessage = reviewMessage;
        this.dateSent = dateSent;
    }

    /** @return The unique ID of the review. */
    public int getReviewId() {
        return reviewId;
    }

    /** @param reviewId Sets the ID to the review. */
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    /** @return The rating of the review. */
    public int getRating() {
        return rating;
    }

    /** @param rating Sets the rating to the review. */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /** @return The ID for the sender of the review. */
    public User getUserId() {
        return userId;
    }

    /** @param userId Sets the ID of the sender. */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /** @return The ID for the post. */
    public Post getPostId() {
        return postId;
    }

    /** @param postId Sets the ID of the post */
    public void setPostId(Post postId) {
        this.postId = postId;
    }

    /** @return The message included in the review. */
    public String getReviewMessage() {
        return reviewMessage;
    }

    /** @param reviewMessage Sets the message of the review. */
    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    /** @return The date in which the review was sent. */
    public Date getDateSent() {
        return dateSent;
    }
    
    /** @param dateSent Sets the date in which the review was sent. */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * Computes a hash code for this review based on its attributes.
     * Used for hashing in collections.
     * 
     * @return Hash code of the message.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.reviewId;
        hash = 83 * hash + this.rating;
        hash = 83 * hash + Objects.hashCode(this.userId);
        hash = 83 * hash + Objects.hashCode(this.postId);
        hash = 83 * hash + Objects.hashCode(this.reviewMessage);
        hash = 83 * hash + Objects.hashCode(this.dateSent);
        return hash;
    }

    /**
     * Checks if two Review objects are equal based on their attributes.
     * 
     * @param obj The object to compare with.
     * @return true if both reviews are equal, false otherwise.
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
        final Review other = (Review) obj;
        if (this.reviewId != other.reviewId) {
            return false;
        }
        if (this.rating != other.rating) {
            return false;
        }
        if (!Objects.equals(this.reviewMessage, other.reviewMessage)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.postId, other.postId)) {
            return false;
        }
        if (!Objects.equals(this.dateSent, other.dateSent)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the Review object.
     * 
     * @return A formatted string with review details.
     */
    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", rating=" + rating + ", userId=" + userId + ", postId=" + postId + ", reviewMessage=" + reviewMessage + ", dateSent=" + dateSent + '}';
    }
    
}
