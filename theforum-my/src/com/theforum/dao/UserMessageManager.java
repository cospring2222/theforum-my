package com.theforum.dao;

/**
* @author Uliana and David
*/

import com.theforum.entities.UserMessages;
import java.util.List;

public interface UserMessageManager {

 
    public List<UserMessages> loadAllUserMessages();
 
    public void saveOrUpdateUserMessage(UserMessages um);
 
    public UserMessages findUserMessageById(Long id);
 
    public void deleteUserMessage(UserMessages um);

}
