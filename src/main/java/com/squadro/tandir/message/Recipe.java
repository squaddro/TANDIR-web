package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
	private String recipe_id;
	private String recipe_name;
	private String recipe_desc;
	private String user_name;
	private String[] uris;
	
	public Recipe(){
		// nothing
	}
	
	public Recipe(String recipe_id, String recipe_name, String recipe_desc, String user_name, String[] uris) {
		this.recipe_id = recipe_id;
		this.recipe_name = recipe_name;
		this.recipe_desc = recipe_desc;
		this.user_name = user_name;
		this.uris = uris;
	}
	
	public String getRecipe_id() {
		return recipe_id;
	}
	
	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}
	
	public String getRecipe_name() {
		return recipe_name;
	}
	
	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}
	
	public String getRecipe_desc() {
		return recipe_desc;
	}
	
	public void setRecipe_desc(String recipe_desc) {
		this.recipe_desc = recipe_desc;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String[] getURIs() {
		return uris;
	}
	
	public void setURIs(String [] uris) {
		this.uris = uris;
	}
}