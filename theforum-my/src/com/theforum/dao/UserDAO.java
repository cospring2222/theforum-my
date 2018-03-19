package com.theforum.dao;


import com.theforum.entities.Users;

 
/**
 *
 * @author Uliana and David
 */
public interface UserDAO extends GenericDAO<Users, Long> {
	public Users findByName(String username);
}
