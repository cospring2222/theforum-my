package com.theforum.json;
public class UserWrapper {
	private Long id;
	private String username;
	private String role;
	//	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private String token;
	
	
	public UserWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWrapper(Long id, String username, String role, String firstname, String lastname, String password,
			String token) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}	
	
}