package com.theforum.dao;
/**
 * @author Uliana and David
 */

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.theforum.entities.Forums;
import com.theforum.util.HibernateUtil;

public class ForumManagerImpl implements ForumManager {
 
    private ForumDAO forumDAO = new ForumDAOImpl();
 

    public List<Forums> loadAllForums() {
        List<Forums> allTheams = new ArrayList<Forums>();
        try {
            HibernateUtil.beginTransaction();
            allTheams = forumDAO.findAll(Forums.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
        	ex.printStackTrace();

            System.out.println("Error: Can't load forums");
        }
        return allTheams;
    }
 
    public void saveOrUpdateForum(Forums forum) {
        try {
            HibernateUtil.beginTransaction();
            forumDAO.save(forum);
            HibernateUtil.commitTransaction();
            System.out.println("Forum created or updated sucsessffully");
        } catch (HibernateException ex) {
        	ex.printStackTrace();

            System.out.println("Error: Cant't create or update forum.");
            HibernateUtil.rollbackTransaction();
        }
    }
 
    public Forums findForumById(Long id) {
    	Forums forum = null;
        try {
            HibernateUtil.beginTransaction();
            forum = (Forums) forumDAO.findByID(Forums.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
        	ex.printStackTrace();

            System.out.println("Error: can't find forum");
        }
        return forum;
    }
 
    public void deleteForum(Forums forum) {
        try {
            HibernateUtil.beginTransaction();
            forumDAO.delete(forum);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
        	ex.printStackTrace();

            System.out.println("Error: can't delete forum");
            HibernateUtil.rollbackTransaction();
        }
    }

}
