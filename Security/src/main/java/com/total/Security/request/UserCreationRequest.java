package com.total.Security.request;

import javax.validation.constraints.NotNull;

import com.total.Security.utils.UserRole;

public class UserCreationRequest {
	
	@NotNull(message = "Name cannot be null")
	private String name;
	private String email;
	private String username;
	private String password;
	@NotNull(message = "Role cannot be null")
	private UserRole userRole;
	
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
