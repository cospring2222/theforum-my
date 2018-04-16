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
        String hql = "SELECT p FROM Users p WHERE p.username = :username";
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("username", username);
        u = findOne(query);
        return u;
    }

	@Override
	public void increaseCommentCounter(Long userId) {
		String hql = "Update Users f set f.userCommentNumber=(f.userCommentNumber + 1) where f.userId =:userId";;
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("userId", userId);
        int result = query.executeUpdate();		
	}
	
	@Override
	public void decreaseCommentCounter(Long userId) {
        String hql = "Update Users f set f.userCommentNumber=(f.userCommentNumber - 1) where f.userId =:userId";;
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("userId", userId);
        int result = query.executeUpdate();	
		
	}



}

