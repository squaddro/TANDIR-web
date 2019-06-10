package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String user_name;
	private Recipe[] recipes;
	
	public User(String user_name, Recipe[] recipes){
		this.user_name = user_name;
		this.recipes = recipes;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public Recipe[] getRecipes() {
		return recipes;
	}
	
	public void setRecipes(Recipe[] recipes) {
		this.recipes = recipes;
	}
}