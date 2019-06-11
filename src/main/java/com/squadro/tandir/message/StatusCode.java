package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum StatusCode {
	SIGNIN_SUCCESSFULL(100, "Signin successfull!"),
	SIGNIN_REJECT(101, "Username/password did not match!"),
	SIGNUP_SUCCESSFULL(102, "Signup successfull!"),
	SIGNUP_REJECT(103, "Signup could not be done!"),
	SIGNUP_REJECT_USERNAME(104, "Username allready exists!"),
	SIGNUP_REJECT_PASSWORD(105, "Password should be in the format!"),
	RECIPE_ADDED(106, "Recipe is successfully added!"),
	RECIPE_NOT_ADDED(107, "Recipe could not be added!"),
	REJECT_ALLREADY_LOGGED_IN(108, "You are allready logged in!"),
	REJECT_NOT_LOGGED_IN(109, "You are not logged in!"),
	SIGNIN_REJECT_COOKIE_NOTSET(110, "Cookie could not be set!");
	
	private StatusCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	private int status;
	private String message;
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return status + " " + message;
	}
}