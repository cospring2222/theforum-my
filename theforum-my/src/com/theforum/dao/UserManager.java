package com.theforum.dao;

/**
* @author Uliana and David
*/

import com.theforum.entities.Users;
import java.util.List;

public interface UserManager {

    public Users findByUserName(String username);
 
    public List<Users> loadAllUsers();
 
    public void saveOrUpdateUser(Users u);
 
    public Users findUserById(Long id);
 
    public void deleteUser(Users u);

}
