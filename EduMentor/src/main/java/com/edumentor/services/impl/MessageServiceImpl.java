package com.edumentor.services.impl;

import com.edumentor.dao.MessageDaoIntf;
import com.edumentor.dao.impl.MessageDaoImpl;
import com.edumentor.models.Message;
import com.edumentor.services.MessageServiceIntf;
import java.util.List;

/**
 * Implementation of the {@link MessageServiceIntf} interface.
 * Provides methods for managing {@link Message} entities, including adding, retrieving, 
 * deleting messages, and counting messages for a user.
 * 
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * It interacts with the {@link MessageDaoIntf} to perform database operations.
 * 
 * @author adrian
 */
public class MessageServiceImpl implements MessageServiceIntf {

    // Reference to the DAO layer for message operations
    private final MessageDaoIntf messageDao = MessageDaoImpl.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private MessageServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link MessageServiceImpl}.
     *
     * @return The singleton instance of {@link MessageServiceImpl}.
     */
    public static MessageServiceImpl getInstance() {
        return MessageServiceImplHolder.INSTANCE;
    }

    /**
     * Adds a new {@link Message} to the database.
     *
     * @param message The {@link Message} object to be added.
     */
    @Override
    public void addMessage(Message message) {
        messageDao.addMessage(message);
    }

    /**
     * Retrieves a specific {@link Message} by its unique ID.
     *
     * @param messageId The ID of the {@link Message} to be retrieved.
     * @return The {@link Message} object if found, or null if no such ID exists.
     */
    @Override
    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    /**
     * Retrieves all {@link Message} objects associated with a specific user by their user ID.
     *
     * @param userId The ID of the user whose messages are to be retrieved.
     * @return A {@link List} of {@link Message} objects sent or received by the specified user.
     */
    @Override
    public List<Message> getMessagesByUserId(int userId) {
        return messageDao.getMessagesByUserId(userId);
    }

    /**
     * Retrieves all {@link Message} objects exchanged between two users.
     *
     * @param senderId   The ID of the sender user.
     * @param receiverId The ID of the receiver user.
     * @return A {@link List} of {@link Message} objects exchanged between the two users.
     */
    @Override
    public List<Message> getMessagesBetweenUsers(int senderId, int receiverId) {
        return messageDao.getMessagesBetweenUsers(senderId, receiverId);
    }

    /**
     * Deletes a specific {@link Message} by its unique ID.
     *
     * @param messageId The ID of the {@link Message} to be deleted.
     */
    @Override
    public void deleteMessage(int messageId) {
        messageDao.deleteMessage(messageId);
    }

    /**
     * Counts the number of messages associated with a specific user by their user ID.
     *
     * @param userId The ID of the user whose messages are to be counted.
     * @return The total number of messages associated with the specified user.
     */
    @Override
    public int countMessagesByUserId(int userId) {
        return messageDao.countMessagesByUserId(userId);
    }

    /**
     * Retrieves a limited number of recent {@link Message} objects for a specific user.
     *
     * @param userId The ID of the user whose recent messages are to be retrieved.
     * @param limit  The maximum number of recent messages to retrieve.
     * @return A {@link List} of recent {@link Message} objects for the specified user, up to the given limit.
     */
    @Override
    public List<Message> getRecentMessages(int userId, int limit) {
        return messageDao.getRecentMessages(userId, limit);
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link MessageServiceImpl}.
     */
    private static class MessageServiceImplHolder {

        private static final MessageServiceImpl INSTANCE = new MessageServiceImpl();
    }
}
