package com.theforum.dao;

/**
 * @author Uliana and David
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.theforum.entities.Topics;
import com.theforum.util.HibernateUtil;

public class TopicManagerImpl implements TopicManager {
 
    private TopicDAO topicDAO = new TopicDAOImpl();
 

    public List<Topics> loadAllTopics() {
        List<Topics> allTopics = new ArrayList<Topics>();
        try {
            HibernateUtil.beginTransaction();
            allTopics = topicDAO.findAll(Topics.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: Can't load forums");
        }
        return allTopics;
    }
 
    public List<Topics> loadAllTopicsByForumID(Long forumID)
    {
        List<Topics> allTopicsByForumID = new ArrayList<Topics>();
        try {
            HibernateUtil.beginTransaction();
            allTopicsByForumID = topicDAO.findAllByForumID(forumID);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: Can't load forums");
        }
        return allTopicsByForumID;
    }


    
    public void saveOrUpdateTopic(Topics topic) {
        try {
            HibernateUtil.beginTransaction();
            topicDAO.save(topic);
            HibernateUtil.commitTransaction();
            System.out.println("Topic created or updated sucsessffully");
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't create or update topic.");
            HibernateUtil.rollbackTransaction();
        }
    }
 
    public Topics findTopicById(Long id) {
    	Topics topic = null;
        try {
            HibernateUtil.beginTransaction();
            topic = (Topics) topicDAO.findByID(Topics.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: can't find topic");
        }
        return topic;
    }
 
    public void deleteTopic(Topics topic) {
        try {
            HibernateUtil.beginTransaction();
            topicDAO.delete(topic);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error: can't delete topic");
            HibernateUtil.rollbackTransaction();
        }
    }

}
