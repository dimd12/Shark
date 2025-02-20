package com.edumentor.dao.impl;

import com.edumentor.dao.QuestionDaoIntf;
import com.edumentor.db.DataSource;
import com.edumentor.models.Category;
import com.edumentor.models.Question;
import com.edumentor.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the {@link QuestionDaoIntf} interface for managing {@link Question} entities
 * in the database. This class provides methods to save, delete, retrieve, and search questions.
 *
 * <p>It uses a singleton pattern to ensure only one instance of the DAO exists.</p>
 *
 * @author adrian
 */
public class QuestionDaoImpl implements QuestionDaoIntf {

    private static final Logger LOG = Logger.getLogger(QuestionDaoImpl.class.getName());

    private final DataSource dataSource = DataSource.getInstance();

    /**
     * Private constructor to prevent direct instantiation.  This enforces the singleton pattern.
     */
    private QuestionDaoImpl() {
    }

    /**
     * Returns the singleton instance of the {@code QuestionDaoImpl}.
     *
     * @return The singleton instance.
     */
    public static QuestionDaoImpl getInstance() {
        return QuestionDaoImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Question} to the database.
     *
     * @param question The {@link Question} object to be saved. The question object's userId, title, details,
     *                 imageUrl, dateCreated, and categoryId fields will be persisted.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public void save(Question question) {
        String sql = "INSERT INTO questions (user_id, title, details, image_url, date_created, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, question.getUserId().getUserId());
            statement.setString(2, question.getTitle());
            statement.setString(3, question.getDetails());
            statement.setString(4, question.getImageUrl());
            statement.setDate(5, question.getDateCreated());
            statement.setInt(6, question.getCategoryId().getCategoryId());

            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error saving question: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a {@link Question} from the database based on its unique ID.
     *
     * @param questionId The ID of the {@link Question} to delete.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public void delete(int questionId) {
        String sql = "DELETE FROM questions WHERE question_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionId);

            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error deleting question: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_QUESTION_QUERY =
            "SELECT * FROM questions " +
                    "LEFT JOIN users ON questions.user_id = users.user_id " +
                    "LEFT JOIN categories ON questions.category_id = categories.category_id ";

    /**
     * Retrieves all {@link Question} objects from the database.
     *
     * @return A {@link List} of all {@link Question} objects. Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> findAll() {
        String sql = BASE_QUESTION_QUERY;
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                question = mapResultSetToQuestion(rs);
                questions.add(question);
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding all questions: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return questions;
    }

    /**
     * Finds a {@link Question} by its unique ID.
     *
     * @param questionId The ID of the {@link Question} to find.
     * @return The {@link Question} object if found, otherwise {@code null}.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public Question findById(int questionId) {
        String sql = BASE_QUESTION_QUERY + "WHERE question_id = ?";
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding question by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return question;
    }

    /**
     * Finds all {@link Question} objects created by a specific {@link User}.
     *
     * @param userId The ID of the {@link User} to find questions for.
     * @return A {@link List} of {@link Question} objects created by the given user. Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> findByUserId(int userId) {
        String sql = BASE_QUESTION_QUERY + "WHERE questions.user_id = ?";
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding questions by user ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return questions;
    }

    /**
     * Finds all {@link Question} objects belonging to a specific {@link Category}.
     *
     * @param categoryId The ID of the {@link Category} to find questions for.
     * @return A {@link List} of {@link Question} objects belonging to the given category. Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> findByCategoryId(int categoryId) {
        String sql = BASE_QUESTION_QUERY + "WHERE questions.category_id = ?";
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding questions by category ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return questions;
    }

    /**
     * Finds all {@link Question} objects that match a specific title.
     *
     * @param title The title to search for.
     * @return A {@link List} of {@link Question} objects that match the given title. Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> findByTitle(String title) {
        String sql = BASE_QUESTION_QUERY + "WHERE title = ?";
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding questions by title: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return questions;
    }

    /**
     * Finds all {@link Question} objects created on a specific date.
     *
     * @param dateCreated The date to search for.
     * @return A {@link List} of {@link Question} objects created on the given date. Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> findByDateRange(Date dateCreated) {
        String sql = BASE_QUESTION_QUERY + "WHERE date_created = ?";
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, dateCreated);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error finding questions by date range: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return questions;
    }

    /**
     * Searches for {@link Question} objects based on a search term in the title or details.
     *
     * @param searchTerm The search term to look for in the title or details of the questions.
     * @return A {@link List} of {@link Question} objects that match the search term.  Returns an empty list if no questions are found.
     * @throws RuntimeException if a SQLException occurs during the database operation.
     */
    @Override
    public List<Question> searchQuestions(String searchTerm) {
        String sql = BASE_QUESTION_QUERY + "WHERE title LIKE ? OR details LIKE ?";
        List<Question> questions = new ArrayList<>();
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
            connection.close();
        } catch (SQLException e) {
            LOG.severe("Error searching questions: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return questions;
    }

    /**
     * Maps a {@link ResultSet} row to a {@link Question} object.
     *
     * @param rs The {@link ResultSet} to map from. It's expected that the ResultSet contains columns
     *           named "question_id", "user_id", "username", "title", "details", "image_url", "date_created",
     *           "category_id", and "category_name".
     * @return A {@link Question} object populated with data from the {@link ResultSet}.
     * @throws SQLException if a database access error occurs or if a column label
     *                      cannot be found in the {@link ResultSet}.
     */
    private Question mapResultSetToQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setQuestionId(rs.getInt("question_id"));

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        question.setUserId(user);

        question.setTitle(rs.getString("title"));
        question.setDetails(rs.getString("details"));
        question.setImageUrl(rs.getString("image_url"));
        question.setDateCreated(rs.getDate("date_created"));

        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        question.setCategoryId(category);

        return question;
    }

    /**
     * Holder class to implement the singleton pattern.
     */
    private static class QuestionDaoImplHolder {

        private static final QuestionDaoImpl INSTANCE = new QuestionDaoImpl();
    }
}
