package com.edumentor.dao.impl;

import com.edumentor.dao.AnswerDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Answer;
import com.edumentor.models.Question;
import com.edumentor.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link AnswerDaoIntf} interface for managing {@link Answer} entities
 * in the database. This class provides methods to save, delete, and retrieve answers.
 *
 * <p>It uses a singleton pattern to ensure only one instance of the DAO exists.</p>
 *
 * @author adrian
 */
public class AnswerDaoImpl implements AnswerDaoIntf {

    private static final Logger LOG = Logger.getLogger(AnswerDaoImpl.class.getName());

    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent direct instantiation.  This enforces the singleton pattern.
     */
    private AnswerDaoImpl() {
    }

    /**
     * Returns the singleton instance of the {@code AnswerDaoImpl}.
     *
     * @return The singleton instance.
     */
    public static AnswerDaoImpl getInstance() {
        return AnswerDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Answer} to the database.
     *
     * @param answer The {@link Answer} object to be saved.  The answer object's questionId, userId,
     *               response, imageUrl, and dateCreated fields will be persisted.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public void save(Answer answer) {
        String sql = "INSERT INTO answers (question_id, user_id, response, image_url, date_created) " +
                "VALUES (?, ?, ?, ?, ?) ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, answer.getQuestionId().getQuestionId());
            statement.setInt(2, answer.getUserId().getUserId());
            statement.setString(3, answer.getResponse());
            statement.setString(4, answer.getImageUrl());
            statement.setDate(5, answer.getDateCreated());

            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving answer: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes an {@link Answer} from the database based on its unique ID.
     *
     * @param answerId The ID of the {@link Answer} to delete.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public void delete(int answerId) {
        String sql = "DELETE FROM answers WHERE answer_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, answerId);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting answer: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_ANSWER_QUERY =
            "SELECT * FROM answers " +
                    "LEFT JOIN questions ON answers.question_id = questions.question_id " +
                    "LEFT JOIN users ON answers.user_id = users.user_id ";

    /**
     * Finds all {@link Answer} objects associated with a specific {@link Question} ID.
     *
     * @param questionId The ID of the {@link Question} to find answers for.
     * @return A {@link List} of {@link Answer} objects associated with the given question ID.  Returns an empty list if no answers are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Answer> findByQuestionId(int questionId) {
        String sql = BASE_ANSWER_QUERY + "WHERE answers.question_id = ?";
        List<Answer> answers = new ArrayList<>();
        Answer answer = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    answer = mapResultSetToAnswer(rs);
                    answers.add(answer);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding answers by question ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return answers;
    }

    /**
     * Finds all {@link Answer} objects submitted by a specific {@link User} ID.
     *
     * @param userId The ID of the {@link User} to find answers for.
     * @return A {@link List} of {@link Answer} objects associated with the given user ID. Returns an empty list if no answers are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Answer> findByUserId(int userId) {
        String sql = BASE_ANSWER_QUERY + "WHERE user_id = ?";
        List<Answer> answers = new ArrayList<>();
        Answer answer = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    answer = mapResultSetToAnswer(rs);
                    answers.add(answer);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding answers by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return answers;
    }

    /**
     * Maps a {@link ResultSet} row to an {@link Answer} object.
     *
     * @param rs The {@link ResultSet} to map from.  It's expected that the ResultSet contains columns
     *           named "answer_id", "question_id", "user_id", "response", "image_url", and "date_created".
     * @return An {@link Answer} object populated with data from the {@link ResultSet}.
     * @throws SQLException if a database access error occurs or if a column label
     *                      cannot be found in the {@link ResultSet}.
     */
    private Answer mapResultSetToAnswer(ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setAnswerId(rs.getInt("answer_id"));

        Question question = new Question();
        question.setQuestionId(rs.getInt("question_id"));
        answer.setQuestionId(question);

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        answer.setUserId(user);

        answer.setResponse(rs.getString("response"));
        answer.setImageUrl(rs.getString("image_url"));
        answer.setDateCreated(rs.getDate("date_created"));

        return answer;
    }

    /**
     *  Holder class to implement the singleton pattern.
     */
    private static class AnswerDaoImplHolder {

        private static final AnswerDaoImpl INSTANCE = new AnswerDaoImpl();
    }
}
