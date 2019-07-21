package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {
	
	private String recipe_id;
	
	public Like() {}
	
	public Like(String recipe_id) {
		this.setRecipe_id(recipe_id);
	}

	public String getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}
	

}
