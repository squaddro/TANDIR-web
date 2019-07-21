package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credential {
	
	private String user_name;
	private String password;
	private String token;
	
	public Credential() {
		// nothing
	}
	
	public Credential(String user_name, String password, String token) {
		this.user_name = user_name;
		this.password = password;
		this.token = token;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name() {
		this.user_name = user_name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword() {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}