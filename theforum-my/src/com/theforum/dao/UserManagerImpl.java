package com.theforum.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import com.theforum.entities.Users;
import com.theforum.util.HibernateUtil;

public class UserManagerImpl implements UserManager {
 
    private UserDAO userDAO = new UserDAOImpl();
 
    public Users findByUserName(String username) {
        Users u = null;

        try {
            HibernateUtil.beginTransaction();
            u = userDAO.findByName(username);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Handle your error here");
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println("Eror: in findByUserName");
        }
        return u;
    }
 
    public List<Users> loadAllUsers() {
        List<Users> allUsers = new ArrayList<Users>();
        try {
            HibernateUtil.beginTransaction();
            allUsers = userDAO.findAll(Users.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle your error here");
        }
        return allUsers;
    }
 
    public void saveOrUpdateUser(Users u) {
        try {
            HibernateUtil.beginTransaction();
            userDAO.save(u);
            HibernateUtil.commitTransaction();
            System.out.println("User created or updated sucsessffully");
        } catch (HibernateException ex) {
            System.out.println("Error: Cant't create or update user.");
            HibernateUtil.rollbackTransaction();
        }
    }
 
    public Users findUserById(Long id) {
    	Users u = null;
        try {
            HibernateUtil.beginTransaction();
            u = (Users) userDAO.findByID(Users.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle your error here");
        }
        return u;
    }
 
    public void deleteUser(Users u) {
        try {
            HibernateUtil.beginTransaction();
            userDAO.delete(u);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle your error here");
            HibernateUtil.rollbackTransaction();
        }
    }

}
