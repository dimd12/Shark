package com.edumentor.services.impl;

import com.edumentor.dao.PostDaoIntf;
import com.edumentor.dao.QuestionDaoIntf;
import com.edumentor.dao.impl.PostDaoImpl;
import com.edumentor.dao.impl.QuestionDaoImpl;
import com.edumentor.models.Post;
import com.edumentor.models.Question;
import com.edumentor.services.PostServiceIntf;
import com.edumentor.services.QuestionServiceIntf;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link QuestionServiceIntf} interface for managing {@link Question} entities.
 * This class provides methods to save, delete, retrieve, and search questions, delegating the actual data
 * access operations to the {@link QuestionDaoIntf}.
 *
 * <p>It uses a singleton pattern to ensure only one instance of the service exists.</p>
 *
 * @author adrian
 */
public class QuestionServiceImpl implements QuestionServiceIntf {

    // Reference to the DAO layer for post operations
    private final QuestionDaoIntf questionDao = QuestionDaoImpl.getInstance();

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private QuestionServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link QuestionServiceImpl}.
     *
     * @return The singleton instance of {@link QuestionServiceImpl}.
     */
    public static QuestionServiceImpl getInstance() {
        return QuestionServiceImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Question} by delegating to the {@link QuestionDaoIntf}.
     *
     * @param question The {@link Question} object to be saved.
     */
    @Override
    public void save(Question question) {
        questionDao.save(question);
    }

    /**
     * Deletes a {@link Question} by its ID, delegating to the {@link QuestionDaoIntf}.
     *
     * @param questionId The ID of the question to delete.
     */
    @Override
    public void delete(int questionId) {
        questionDao.delete(questionId);
    }

    /**
     * Finds all {@link Question} objects, delegating to the {@link QuestionDaoIntf}.
     *
     * @return A {@link List} of all {@link Question} objects.
     */
    @Override
    public List<Question> findAll() {
        return questionDao.findAll();
    }

    /**
     * Finds a {@link Question} by its ID, delegating to the {@link QuestionDaoIntf}.
     *
     * @param questionId The ID of the question to find.
     * @return The {@link Question} object if found, otherwise {@code null}.
     */
    @Override
    public Question findById(int questionId) {
        return questionDao.findById(questionId);
    }

    /**
     * Finds all {@link Question} objects created by a specific user ID, delegating to the {@link QuestionDaoIntf}.
     *
     * @param userId The ID of the user to find questions for.
     * @return A {@link List} of {@link Question} objects created by the given user ID.
     */
    @Override
    public List<Question> findByUserId(int userId) {
        return questionDao.findByUserId(userId);
    }

    /**
     * Finds all {@link Question} objects belonging to a specific category ID, delegating to the {@link QuestionDaoIntf}.
     *
     * @param categoryId The ID of the category to find questions for.
     * @return A {@link List} of {@link Question} objects belonging to the given category ID.
     */
    @Override
    public List<Question> findByCategoryId(int categoryId) {
        return questionDao.findByCategoryId(categoryId);
    }

    /**
     * Finds all {@link Question} objects that match a specific title, delegating to the {@link QuestionDaoIntf}.
     *
     * @param title The title to search for.
     * @return A {@link List} of {@link Question} objects that match the given title.
     */
    @Override
    public List<Question> findByTitle(String title) {
        return questionDao.findByTitle(title);
    }

    /**
     * Finds all {@link Question} objects created on a specific date, delegating to the {@link QuestionDaoIntf}.
     *
     * @param dateCreated The date to search for.
     * @return A {@link List} of {@link Question} objects created on the given date.
     */
    @Override
    public List<Question> findByDateRange(Date dateCreated) {
        return questionDao.findByDateRange(dateCreated);
    }

    /**
     * Searches for {@link Question} objects based on a search term, delegating to the {@link QuestionDaoIntf}.
     *
     * @param searchTerm The search term to look for in the title or details of the questions.
     * @return A {@link List} of {@link Question} objects that match the search term.
     */
    @Override
    public List<Question> searchQuestions(String searchTerm) {
        return questionDao.searchQuestions(searchTerm);
    }


    /**
     * Inner static class responsible for holding the singleton instance of {@link QuestionServiceImpl}.
     */
    private static class QuestionServiceImplHolder {

        private static final QuestionServiceImpl INSTANCE = new QuestionServiceImpl();
    }
}
