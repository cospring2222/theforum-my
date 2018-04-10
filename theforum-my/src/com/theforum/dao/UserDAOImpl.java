package com.theforum.dao;

/**
 * @author Uliana and David
 */
import com.theforum.entities.Users;
import com.theforum.util.HibernateUtil;
import java.math.BigDecimal;
import org.hibernate.Query;
 
public class UserDAOImpl extends GenericDAOImpl<Users, Long> implements UserDAO {
    public Users findByName(String username) {
        Users u = null;
        String sql = "SELECT p FROM Users p WHERE p.username = :username";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("username", username);
        u = findOne(query);
        return u;
    }

}

