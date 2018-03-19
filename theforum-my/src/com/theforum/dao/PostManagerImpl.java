package com.theforum.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.theforum.entities.Posts;
import com.theforum.util.HibernateUtil;

public class PostManagerImpl implements PostManager {
 
    private  PostDAO postDAO = new  PostDAOImpl();

    public List<Posts> loadAllPosts() {
        List<Posts> allPosts = new ArrayList<Posts>();
        try {
            HibernateUtil.beginTransaction();
            allPosts = postDAO.findAll(Posts.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Can't load posts");
        }
        return allPosts;
    }
 
    public void saveOrUpdatePost(Posts u) {
        try {
            HibernateUtil.beginTransaction();
            postDAO.save(u);
            HibernateUtil.commitTransaction();
            System.out.println("Post created or updated sucsessffully");
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't create or update post.");
            HibernateUtil.rollbackTransaction();
        }
    }
 
    public Posts findPostById(Long id) {
    	Posts p = null;
        try {
            HibernateUtil.beginTransaction();
            p = (Posts) postDAO.findByID(Posts.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Can't find post");
        }
        return p;
    }
 
    public void deletePost(Posts p) {
        try {
            HibernateUtil.beginTransaction();
            postDAO.delete(p);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Can't delete post");
            HibernateUtil.rollbackTransaction();
        }
    }



}
