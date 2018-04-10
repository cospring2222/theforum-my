package com.theforum.dao;

/**
 * @author Uliana and David
 */
import com.theforum.entities.Topics;

import java.util.List;

public interface TopicManager {

 
    public List<Topics> loadAllTopics();
 
    public List<Topics> loadAllTopicsByForumID(Long forumID);
    
    public void saveOrUpdateTopic(Topics topic);
 
    public Topics findTopicById(Long id);
 
    public void deleteTopic(Topics topic);

}
