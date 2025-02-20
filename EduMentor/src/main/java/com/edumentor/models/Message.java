package com.edumentor.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a message exchanged between two users in the application.
 * Each message has a sender, a receiver, text content, and a timestamp.
 * 
 * @author adrian
 */
public class Message {

    /** Unique identifier for the message (Primary Key). */
    private int messageId;
    /** 
     * The user who sent the message (Foreign Key referencing User). 
     * @see User
     */
    private User senderId;
    /** 
     * The user who received the message (Foreign Key referencing User). 
     * @see User
     */
    private User receiverId;
    /** The actual text content of the message. */
    private String text;
    /** The timestamp when the message was sent (default: current time). */
    private Date dateSent;
    
    /** Default constructor. */
    public Message() {
    }

    /**
     * Parameterized constructor to initialize a Message object.
     * 
     * @param messageId Unique identifier for the message.
     * @param senderId  The sender of the message.
     * @param receiverId The receiver of the message.
     * @param text The message content.
     * @param dateSent The timestamp of when the message was sent.
     */
    public Message(int messageId, User senderId, User receiverId, String text, Date dateSent) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.dateSent = dateSent;
    }

    /** @return The unique ID of the message. */
    public int getMessageId() {
        return messageId;
    }

    /** @param messageId Sets the message ID. */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /** @return The sender of the message. */
    public User getSenderId() {
        return senderId;
    }

    /** @param senderId Sets the sender of the message. */
    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    /** @return The receiver of the message. */
    public User getReceiverId() {
        return receiverId;
    }

    /** @param receiverId Sets the receiver of the message. */
    public void setReceiverId(User receiverId) {
        this.receiverId = receiverId;
    }

    /** @return The text content of the message. */
    public String getText() {
        return text;
    }

    /** @param text Sets the text content of the message. */
    public void setText(String text) {
        this.text = text;
    }

    /** @return The timestamp of when the message was sent. */
    public Date getDateSent() {
        return dateSent;
    }

    /** @param dateSent Sets the timestamp of the message. */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * Computes a hash code for this message based on its attributes.
     * Used for hashing in collections.
     * 
     * @return Hash code of the message.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.messageId;
        hash = 71 * hash + Objects.hashCode(this.senderId);
        hash = 71 * hash + Objects.hashCode(this.receiverId);
        hash = 71 * hash + Objects.hashCode(this.text);
        hash = 71 * hash + Objects.hashCode(this.dateSent);
        return hash;
    }

    /**
     * Checks if two Message objects are equal based on their attributes.
     * 
     * @param obj The object to compare with.
     * @return true if both messages are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        return this.messageId == other.messageId &&
               Objects.equals(this.senderId, other.senderId) &&
               Objects.equals(this.receiverId, other.receiverId) &&
               Objects.equals(this.text, other.text) &&
               Objects.equals(this.dateSent, other.dateSent);
    }

    /**
     * Returns a string representation of the Message object.
     * 
     * @return A formatted string with message details.
     */
    @Override
    public String toString() {
        return "Message{" +
               "messageId=" + messageId +
               ", senderId=" + senderId +
               ", receiverId=" + receiverId +
               ", text='" + text + '\'' +
               ", dateSent=" + dateSent +
               '}';
    }
}
