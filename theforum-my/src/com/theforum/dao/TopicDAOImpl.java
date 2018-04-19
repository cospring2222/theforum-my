package com.theforum.dao;

/**
 * @author Uliana and David
 */
import com.theforum.entities.Topics;
import com.theforum.entities.Users;
import com.theforum.util.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

public class TopicDAOImpl extends GenericDAOImpl<Topics, Long> implements TopicDAO {
    public List<Topics> findAllByForumID(Long forumID) {
    	List<Topics> topics = new ArrayList<Topics>();
        String sql = "SELECT t FROM Topics t WHERE t.forums.forumId = :forumID";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("forumID", forumID);
        topics = query.list();

        //String hql = "SELECT p FROM Users p WHERE p.user_id = :id";
        //query = HibernateUtil.getSession().createQuery(hql).setParameter("id", id);
        //Users user = findOne(query);
        
        return topics;

    }

	@Override
	public void increaseWatcherCounter(Long topicId) {
		String hql = "Update Topics f set f.topicWatcherNumber=(f.topicWatcherNumber + 1) where f.topicId =:topicId";;
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("topicId", topicId);
        int result = query.executeUpdate();		
	}

	@Override
	public void increaseCommentCounter(Long topicId) {
		String hql = "Update Topics f set f.topicCommentNumber=(f.topicCommentNumber + 1) where f.topicId =:topicId";;
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("topicId", topicId);
        int result = query.executeUpdate();		
	}
	
	@Override
	public void decreaseCommentCounter(Long topicId) {
        String hql = "Update Topics f set f.topicCommentNumber=(f.topicCommentNumber - 1) where f.topicId =:topicId";;
        Query query = HibernateUtil.getSession().createQuery(hql).setParameter("topicId", topicId);
        int result = query.executeUpdate();	
		
	}
}

