package com.edumentor.dao;

import com.edumentor.models.Message;
import java.util.List;

/**
 * Interface for managing {@link Message} entities in the database.
 * Provides methods for adding, retrieving, deleting, and counting messages,
 * as well as retrieving messages based on specific criteria.
 * 
 * @author adrian
 */
public interface MessageDaoIntf {

    /**
     * Adds a new message to the database.
     *
     * @param message The {@link Message} object to be added.
     */
    void addMessage(Message message);

    /**
     * Retrieves a message from the database by its unique ID.
     *
     * @param messageId The ID of the message to be retrieved.
     * @return The {@link Message} object if found, or null if no message exists with the given ID.
     */
    Message getMessageById(int messageId);

    /**
     * Retrieves all messages sent or received by a specific user.
     *
     * @param userId The ID of the user whose messages are to be retrieved.
     * @return A {@link List} of {@link Message} objects associated with the given user.
     */
    List<Message> getMessagesByUserId(int userId);

    /**
     * Retrieves all messages exchanged between two users.
     *
     * @param senderId   The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return A {@link List} of {@link Message} objects exchanged between the two users.
     */
    List<Message> getMessagesBetweenUsers(int senderId, int receiverId);

    /**
     * Deletes a message from the database by its unique ID.
     *
     * @param messageId The ID of the message to be deleted.
     */
    void deleteMessage(int messageId);

    /**
     * Counts the total number of messages associated with a specific user.
     *
     * @param userId The ID of the user whose messages are to be counted.
     * @return The total number of messages associated with the given user.
     */
    int countMessagesByUserId(int userId);

    /**
     * Retrieves a limited number of recent messages for a specific user.
     *
     * @param userId The ID of the user whose recent messages are to be retrieved.
     * @param limit  The maximum number of recent messages to retrieve.
     * @return A {@link List} of recent {@link Message} objects for the given user, up to the specified limit.
     */
    List<Message> getRecentMessages(int userId, int limit);
}
