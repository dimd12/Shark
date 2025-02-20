package com.edumentor.services;

import com.edumentor.models.Answer;

import java.util.List;

/**
 * Interface for managing {@link Answer} entities in the database.
 * Provides methods for saving, deleting, and retrieving answers based on various criteria,
 * such as question ID and user ID.
 *
 * @author adrian
 */
public interface AnswerServiceIntf {

    /**
     * Saves a new answer or updates an existing answer in the database.
     *
     * @param answer The {@link Answer} object to be saved or updated.
     */
    void save(Answer answer);

    /**
     * Deletes an answer from the database by its unique ID.
     *
     * @param answerId The ID of the answer to be deleted.
     */
    void delete(int answerId);

    /**
     * Retrieves all answers associated with a specific question.
     *
     * @param questionId The ID of the question whose answers are to be retrieved.
     * @return A {@link List} of {@link Answer} objects associated with the given question.
     */
    List<Answer> findByQuestionId(int questionId);

    /**
     * Retrieves all answers provided by a specific user.
     *
     * @param userId The ID of the user whose answers are to be retrieved.
     * @return A {@link List} of {@link Answer} objects associated with the given user.
     */
    List<Answer> findByUserId(int userId);
}
