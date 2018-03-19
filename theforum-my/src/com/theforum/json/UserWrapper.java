package com.theforum.json;

/*
 *     id: number;
    username: string;
    role: string;
    email: string;
    firstName: string;
    lastName: string;
 */
public class UserWrapper {
	private Long id;
	private String username;
//	private String email;
	private String firstname;
	private String lastname;
	private String password;
	
	
	public UserWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserWrapper(Long id, String username, String firstname, String lastname, String password) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

	
	
}
