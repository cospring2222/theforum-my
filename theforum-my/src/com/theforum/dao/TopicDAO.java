package com.theforum.dao;


/**
 * @author Uliana and David
 */
import java.util.List;

import com.theforum.entities.Topics;


public interface TopicDAO extends GenericDAO<Topics, Long> {
	public List<Topics> findAllByForumID(Long forumID);
}
