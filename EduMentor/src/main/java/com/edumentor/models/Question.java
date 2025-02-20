package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a question posted by a user in the system.
 * Each question has an ID, a user who posted it, a title, detailed content,
 * an optional image URL, a timestamp of creation, and an associated category.
 *
 * @author adrian
 */
public class Question {

    /** Unique identifier for the question (Primary Key). */
    int questionId;

    /** The user who created the question (Foreign Key references User). */
    User userId;

    /** The title or subject of the question. */
    String title;

    /** Detailed content or description of the question. */
    String details;

    /** URL to an optional image associated with the question. */
    String imageUrl;

    /** Date and time when the question was created. */
    Date dateCreated;

    /** The category to which this question belongs (Foreign Key references Category). */
    Category categoryId;

    /** Default constructor. */
    public Question() {
    }

    /**
     * Parameterized constructor to initialize a Question object.
     *
     * @param questionId Unique identifier for the question.
     * @param userId The user who created the question.
     * @param title The title of the question.
     * @param details Detailed content of the question.
     * @param imageUrl URL for an optional image associated with the question.
     * @param dateCreated The timestamp of when the question was created.
     * @param categoryId The category to which this question belongs.
     */
    public Question(int questionId, User userId, String title, String details, String imageUrl, Date dateCreated, Category categoryId) {
        this.questionId = questionId;
        this.userId = userId;
        this.title = title;
        this.details = details;
        this.imageUrl = imageUrl;
        this.dateCreated = dateCreated;
        this.categoryId = categoryId;
    }

    /** @return The unique ID of the question. */
    public int getQuestionId() {
        return questionId;
    }

    /** @param questionId Sets the unique ID of the question. */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /** @return The user who created the question. */
    public User getUserId() {
        return userId;
    }

    /** @param userId Sets the user who created the question. */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /** @return The title of the question. */
    public String getTitle() {
        return title;
    }

    /** @param title Sets the title of the question. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return Detailed content of the question. */
    public String getDetails() {
        return details;
    }

    /** @param details Sets detailed content of the question. */
    public void setDetails(String details) {
        this.details = details;
    }

    /** @return URL for an optional image associated with the question. */
    public String getImageUrl() {
        return imageUrl;
    }

    /** @param imageUrl Sets URL for an optional image associated with the question. */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** @return Timestamp of when the question was created. */
    public Date getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated Sets the timestamp of when the question was created. */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return The category to which this question belongs. */
    public Category getCategoryId() {
        return categoryId;
    }

    /** @param categoryId Sets the category to which this question belongs. */
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Computes a hash code for this Question based on its attributes.
     *
     * @return Hash code of the Question object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.questionId;
        hash = 23 * hash + Objects.hashCode(this.userId);
        hash = 23 * hash + Objects.hashCode(this.title);
        hash = 23 * hash + Objects.hashCode(this.details);
        hash = 23 * hash + Objects.hashCode(this.imageUrl);
        hash = 23 * hash + Objects.hashCode(this.dateCreated);
        hash = 23 * hash + Objects.hashCode(this.categoryId);
        return hash;
    }

    /**
     * Checks if two Question objects are equal based on their attributes.
     *
     * @param obj The object to compare with.
     * @return true if both questions are equal, false otherwise.
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
        final Question other = (Question) obj;
        if (this.questionId != other.questionId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
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
     * Returns a string representation of the Question object.
     *
     * @return A formatted string with details about the Question.
     */
   @Override
   public String toString() {
       return "Question{" +
               "questionId=" + questionId +
               ", user_id=" + userId +
               ", title='" + title + '\'' +
               ", details='" + details + '\'' +
               ", image_url='" + imageUrl + '\'' +
               ", dateCreated=" + dateCreated +
               ", categoryId=" + categoryId +
               '}';
   }
}
