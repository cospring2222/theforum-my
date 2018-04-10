package com.theforum.dao;

/**
 * @author Uliana and David
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.theforum.entities.UserMessages;
import com.theforum.util.HibernateUtil;

public class UserMessageManagerImpl implements UserMessageManager {
 
    private UserMessageDAO userMessageDAO = new UserMessageDAOImpl();
 
 
    public List<UserMessages> loadAllUserMessages() {
        List<UserMessages> allUserMessages = new ArrayList<UserMessages>();
        try {
            HibernateUtil.beginTransaction();
            allUserMessages = userMessageDAO.findAll(UserMessages.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error:Can't load user messages");
        }
        return allUserMessages;
    }
 
    public void saveOrUpdateUserMessage(UserMessages um) {
        try {
            HibernateUtil.beginTransaction();
            userMessageDAO.save(um);
            HibernateUtil.commitTransaction();
            System.out.println("UserMessage created or updated sucsessffully");
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't create or update user message.");
            HibernateUtil.rollbackTransaction();
        }
    }
 
    public UserMessages findUserMessageById(Long id) {
    	UserMessages um = null;
        try {
            HibernateUtil.beginTransaction();
            um = (UserMessages) userMessageDAO.findByID(UserMessages.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't find user message.");
        }
        return um;
    }
 
    public void deleteUserMessage(UserMessages um) {
        try {
            HibernateUtil.beginTransaction();
            userMessageDAO.delete(um);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't delete user message.");
            HibernateUtil.rollbackTransaction();
        }
    }


}
