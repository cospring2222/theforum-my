package com.theforum.dao;

/**
 * @author Uliana and David
 */
import com.theforum.entities.Topics;
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
        return topics;

    }

}

