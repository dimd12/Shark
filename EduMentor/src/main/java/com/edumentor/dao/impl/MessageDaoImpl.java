package com.edumentor.dao.impl;

import com.edumentor.dao.MessageDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Message;
import com.edumentor.models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link MessageDaoIntf} interface.
 * Provides methods for interacting with the database to perform CRUD operations on {@link Message} entities.
 * This class uses a singleton pattern to ensure a single instance is used throughout the application.
 * 
 * Handles operations such as adding, retrieving, deleting, and counting messages,
 * as well as retrieving messages based on specific criteria like user ID or sender/receiver pairs.
 * 
 * @author adrian
 */
public class MessageDaoImpl implements MessageDaoIntf {

    private static final Logger LOG = Logger.getLogger(MessageDaoImpl.class.getName());

    // Singleton instance of the DataSource
    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent instantiation.
     * Use {@link #getInstance()} to get the singleton instance of this class.
     */
    private MessageDaoImpl() {
    }

    /**
     * Returns the singleton instance of {@link MessageDaoImpl}.
     *
     * @return The singleton instance of {@link MessageDaoImpl}.
     */
    public static MessageDaoImpl getInstance() {
        return MessageDaoImplHolder.INSTANCE;
    }

    /**
     * Adds a new message to the database.
     *
     * @param message The {@link Message} object to be added.
     */
    @Override
    public void addMessage(Message message) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, message, date_sent) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, message.getSenderId().getUserId());
            statement.setInt(2, message.getReceiverId().getUserId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, new Timestamp(message.getDateSent().getTime()));

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error adding message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_MESSAGE_QUERY =
            "SELECT * FROM messages " +
                    "LEFT JOIN users ON messages.sender_id = users.user_id " +
                    "LEFT JOIN users ON messages.receiver_id = users.user_id ";

    /**
     * Retrieves a message from the database by its unique ID.
     *
     * @param messageId The ID of the message to be retrieved.
     * @return The {@link Message} object if found, or null if no message exists with the given ID.
     */
    @Override
    public Message getMessageById(int messageId) {
        String sql = BASE_MESSAGE_QUERY + "WHERE message_id = ?";
        Message message = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, messageId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    message = mapResultSetToMessage(rs);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error retrieving message by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return message;
    }

    /**
     * Retrieves all messages sent or received by a specific user.
     *
     * @param userId The ID of the user whose messages are to be retrieved.
     * @return A {@link List} of {@link Message} objects associated with the given user.
     */
    @Override
    public List<Message> getMessagesByUserId(int userId) {
        String sql = BASE_MESSAGE_QUERY + "WHERE sender_id = ? OR receiver_id = ? ORDER BY date_sent ASC";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapResultSetToMessage(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error retrieving messages by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return messages;
    }

    /**
     * Retrieves all messages exchanged between two users.
     *
     * @param senderId   The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return A {@link List} of {@link Message} objects exchanged between the two users.
     */
    @Override
    public List<Message> getMessagesBetweenUsers(int senderId, int receiverId) {
        String sql = BASE_MESSAGE_QUERY + "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY date_sent ASC";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.setInt(3, receiverId);
            statement.setInt(4, senderId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapResultSetToMessage(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error retrieving messages between users: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return messages;
    }

    /**
     * Deletes a message from the database by its unique ID.
     *
     * @param messageId The ID of the message to be deleted.
     */
    @Override
    public void deleteMessage(int messageId) {
        String sql = "DELETE FROM messages WHERE message_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, messageId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Counts the total number of messages associated with a specific user.
     *
     * @param userId The ID of the user whose messages are to be counted.
     * @return The total number of messages associated with the given user.
     */
    @Override
    public int countMessagesByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM messages WHERE sender_id = ? OR receiver_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error counting messages by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Retrieves a limited number of recent messages for a specific user in descending order by date sent.
     *
     * @param userId The ID of the user whose recent messages are to be retrieved.
     * @param limit  The maximum number of recent messages to retrieve.
     * @return A {@link List} of recent {@link Message} objects for the given user.
     */
    @Override
    public List<Message> getRecentMessages(int userId, int limit) {
        String sql = BASE_MESSAGE_QUERY + "WHERE sender_id = ? OR receiver_id = ? ORDER BY date_sent DESC LIMIT ?";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, limit);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapResultSetToMessage(rs));
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error retrieving recent messages: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return messages;
    }

    /**
     * Maps a {@link ResultSet} row to a {@link Message} object.
     *
     * @param rs The {@link ResultSet} containing data from a query result row.
     * @return A {@link Message} object populated with data from the current row of the ResultSet.
     */
    private Message mapResultSetToMessage(ResultSet rs) throws SQLException {
        Message message = new Message();

        message.setMessageId(rs.getInt("message_id"));

        User sender = new User();
        sender.setUserId(rs.getInt("sender_id"));
        message.setSenderId(sender);

        User receiver = new User();
        receiver.setUserId(rs.getInt("receiver_id"));
        message.setReceiverId(receiver);

        message.setText(rs.getString("message"));

        Timestamp timestampSentDate = rs.getTimestamp("date_sent");
        if (timestampSentDate != null) {
            message.setDateSent(new Date(timestampSentDate.getTime()));
        }

        return message;
    }

    /**
     * Inner static class responsible for holding the singleton instance of {@link MessageDaoImpl}.
     */
    private static class MessageDaoImplHolder {

        private static final MessageDaoImpl INSTANCE = new MessageDaoImpl();
    }
}
