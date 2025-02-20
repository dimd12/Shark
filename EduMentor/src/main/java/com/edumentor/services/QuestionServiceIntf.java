package com.edumentor.services;

import com.edumentor.models.Question;

import java.sql.Date;
import java.util.List;


/**
 * Service interface for managing {@link Question} entities in the database.
 * Provides methods for saving, deleting, and retrieving questions based on various criteria,
 * such as user ID, category ID, title, and date range.
 *
 * @author adrian
 */
public interface QuestionServiceIntf {

    /**
     * Saves a new question or updates an existing question in the database.
     *
     * @param question The {@link Question} object to be saved or updated.
     */
    void save(Question question);

    /**
     * Deletes a question from the database by its unique ID.
     *
     * @param questionId The ID of the question to be deleted.
     */
    void delete(int questionId);

    /**
     * Retrieves all questions from the database.
     *
     * @return A {@link List} of all {@link Question} objects.
     */
    List<Question> findAll();

    /**
     * Finds a specific question in the database by its unique ID.
     *
     * @param questionId The ID of the question to be retrieved.
     * @return The {@link Question} object if found, or null if no question exists with the given ID.
     */
    Question findById(int questionId);

    /**
     * Retrieves all questions created by a specific user.
     *
     * @param userId The ID of the user whose questions are to be retrieved.
     * @return A {@link List} of {@link Question} objects associated with the given user.
     */
    List<Question> findByUserId(int userId);

    /**
     * Retrieves all questions belonging to a specific category.
     *
     * @param categoryId The ID of the category whose questions are to be retrieved.
     * @return A {@link List} of {@link Question} objects associated with the given category.
     */
    List<Question> findByCategoryId(int categoryId);

    /**
     * Finds questions that match a specific title or contain the title as part of their content.
     *
     * @param title The title (or part of it) to search for in questions.
     * @return A {@link List} of {@link Question} objects that match the given title.
     */
    List<Question> findByTitle(String title);

    /**
     * Retrieves questions created within a specific date range.
     *
     * @param dateCreated The creation date to filter questions by.
     *                    This can represent a single date or be used in conjunction with other filters for a range.
     * @return A {@link List} of {@link Question} objects created within the specified date range.
     */
    List<Question> findByDateRange(Date dateCreated);

    /**
     * Uses the search bar to find the questions.
     *
     * @param searchTerm The search term entered the search field.
     * @return The list of questions similar to the search.
     */
    List<Question> searchQuestions(String searchTerm);
    }
