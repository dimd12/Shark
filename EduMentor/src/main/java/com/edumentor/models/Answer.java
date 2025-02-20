package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents an answer provided to a question in the system.
 * Each answer has an ID, is associated with a specific question, provided by a user,
 * contains a response (text), an optional image URL, and a timestamp of creation.
 *
 * @author adrian
 */
public class Answer {

    /** Unique identifier for the answer (Primary Key). */
    int answerId;

    /** The question to which this answer belongs (Foreign Key references Question). */
    Question questionId;

    /** The user who provided the answer (Foreign Key references User). */
    User userId;

    /** The textual content of the answer. */
    String response;

    /** URL to an optional image associated with the answer. */
    String ImageUrl;

    /** Date and time when the answer was created. */
    Date dateCreated;

    /** Default constructor. */
    public Answer() {
    }

    /**
     * Parameterized constructor to initialize an Answer object.
     *
     * @param answerId Unique identifier for the answer.
     * @param questionId The question to which this answer belongs.
     * @param userId The user who provided the answer.
     * @param response The textual content of the answer.
     * @param ImageUrl URL for an optional image associated with the answer.
     * @param dateCreated The timestamp of when the answer was created.
     */
    public Answer(int answerId, Question questionId, User userId, String response, String ImageUrl, Date dateCreated) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.userId = userId;
        this.response = response;
        this.ImageUrl = ImageUrl;
        this.dateCreated = dateCreated;
    }

    /** @return The unique ID of the answer. */
    public int getAnswerId() {
        return answerId;
    }

    /** @param answerId Sets the unique ID of the answer. */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    /** @return The question to which this answer belongs. */
    public Question getQuestionId() {
        return questionId;
    }

    /** @param questionId Sets the question to which this answer belongs. */
    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    /** @return The user who provided the answer. */
    public User getUserId() {
        return userId;
    }

    /** @param userId Sets the user who provided the answer. */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /** @return The textual content of the answer. */
    public String getResponse() {
        return response;
    }

    /** @param response Sets the textual content of the answer. */
    public void setResponse(String response) {
        this.response = response;
    }

    /** @return URL for an optional image associated with the answer. */
    public String getImageUrl() {
        return ImageUrl;
    }

    /** @param imageUrl Sets URL for an optional image associated with the answer. */
    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    /** @return Timestamp of when the answer was created. */
    public Date getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated Sets the timestamp of when the answer was created. */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

   /**
     * Computes a hash code for this Answer based on its attributes.
     *
     * @return Hash code of the Answer object.
     */
   @Override
   public int hashCode() {
       int hash = 7;
       hash = 67 * hash + this.answerId;
       hash = 67 * hash + Objects.hashCode(this.questionId);
       hash = 67 * hash + Objects.hashCode(this.userId);
       hash = 67 * hash + Objects.hashCode(this.response);
       hash = 67 * hash + Objects.hashCode(this.ImageUrl);
       hash = 67 * hash + Objects.hashCode(this.dateCreated);
       return hash;
   }

   /**
     * Checks if two Answer objects are equal based on their attributes.
     *
     * @param obj The object to compare with.
     * @return true if both answers are equal, false otherwise.
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
       final Answer other = (Answer) obj;
       if (this.answerId != other.answerId) {
           return false;
       }
       if (!Objects.equals(this.response, other.response)) {
           return false;
       }
       if (!Objects.equals(this.ImageUrl, other.ImageUrl)) {
           return false;
       }
       if (!Objects.equals(this.questionId, other.questionId)) {
           return false;
       }
       if (!Objects.equals(this.userId, other.userId)) {
           return false;
       }
       if (!Objects.equals(this.dateCreated, other.dateCreated)) {
           return false;
       }
       return true;
   }

   /**
     * Returns a string representation of the Answer object.
     *
     * @return A formatted string with details about the Answer.
     */
   @Override
   public String toString() {
       return "Answer{" +
               "answerId=" + answerId +
               ", questionId=" + questionId +
               ", userId=" + userId +
               ", response='" + response + '\'' +
               ", image_url='" + ImageUrl + '\'' +
               ", dateCreated=" + dateCreated +
               '}';
   }
}
