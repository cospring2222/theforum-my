package com.theforum.dao;
/**
 * @author Uliana and David
 */

import com.theforum.entities.Forums;
import java.util.List;

public interface ForumManager {

 
    public List<Forums> loadAllForums();
 
    public void saveOrUpdateForum(Forums forum);
 
    public Forums findForumById(Long id);
 
    public void deleteForum(Forums forum);

}
