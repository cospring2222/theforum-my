package com.theforum.dao;


import java.util.List;

import com.theforum.entities.Topics;

 
/**
 *
 * @author Uliana and David
 */
public interface TopicDAO extends GenericDAO<Topics, Long> {
	public List<Topics> findAllByForumID(Long forumID);
}
