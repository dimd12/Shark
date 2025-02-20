package com.edumentor.services.impl;

import com.edumentor.dao.AnswerDaoIntf;
import com.edumentor.dao.impl.AnswerDaoImpl;
import com.edumentor.models.Answer;
import com.edumentor.services.AnswerServiceIntf;
import java.util.List;

/**
 * Implementation of the {@link AnswerServiceIntf} interface for managing {@link Answer} entities.
 * This class provides methods to save, delete, and retrieve answers, delegating the actual data
 * access operations to the {@link AnswerDaoIntf}.
 *
 * <p>It uses a singleton pattern to ensure only one instance of the service exists.</p>
 *
 * @author adrian
 */
public class AnswerServiceImpl implements AnswerServiceIntf {

    // Reference to the DAO layer for category operations
    private final AnswerDaoIntf answerDao = AnswerDaoImpl.getInstance();

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to access the singleton instance of this class.
     */
    private AnswerServiceImpl() {
    }

    /**
     * Returns the singleton instance of {@link AnswerServiceImpl}.
     *
     * @return The singleton instance of {@link AnswerServiceImpl}.
     */
    public static AnswerServiceImpl getInstance() {
        return AnswerServiceImplHolder.INSTANCE;
    }

    /**
     * Saves a new {@link Answer} by delegating to the {@link AnswerDaoIntf}.
     *
     * @param answer The {@link Answer} object to be saved.
     */
    @Override
    public void save(Answer answer) {
        answerDao.save(answer);
    }

    /**
     * Deletes an {@link Answer} by its ID, delegating to the {@link AnswerDaoIntf}.
     *
     * @param answerId The ID of the answer to delete.
     */
    @Override
    public void delete(int answerId) {
        answerDao.delete(answerId);
    }

    /**
     * Finds all {@link Answer} objects associated with a specific question ID, delegating to the {@link AnswerDaoIntf}.
     *
     * @param questionId The ID of the question to find answers for.
     * @return A {@link List} of {@link Answer} objects associated with the given question ID.
     */
    @Override
    public List<Answer> findByQuestionId(int questionId) {
        return answerDao.findByQuestionId(questionId);
    }

    /**
     * Finds all {@link Answer} objects submitted by a specific user ID, delegating to the {@link AnswerDaoIntf}.
     *
     * @param userId The ID of the user to find answers for.
     * @return A {@link List} of {@link Answer} objects associated with the given user ID.
     */
    @Override
    public List<Answer> findByUserId(int userId) {
        return answerDao.findByUserId(userId);
    }


    /**
     * Inner static class responsible for holding the singleton instance of {@link AnswerServiceImpl}.
     */
    private static class AnswerServiceImplHolder {

        private static final AnswerServiceImpl INSTANCE = new AnswerServiceImpl();
    }
}
