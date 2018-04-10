package com.theforum.dao;

/**
 * @author Uliana and David
 */

import com.theforum.entities.Users;

 

public interface UserDAO extends GenericDAO<Users, Long> {
	public Users findByName(String username);
}
