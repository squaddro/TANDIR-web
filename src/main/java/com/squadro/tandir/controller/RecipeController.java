package com.squadro.tandir.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import com.squadro.tandir.message.Recipe;
import com.squadro.tandir.message.Status;
import com.squadro.tandir.message.StatusCode;
import com.squadro.tandir.service.Database;

@RestController
public class RecipeController {
	
	@RequestMapping(
		value = "/recipe",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Recipe[] recipe() {
		Recipe[] recipes = Database.getAllRecipes();
		return recipes;
	}
	
	@RequestMapping(
		value = "/recipe/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Recipe recipeWithId(@PathVariable("id") String recipeId) {
		Recipe recipe = Database.getRecipe(recipeId);
		return recipe;
	}

	@RequestMapping(
		value = "/search",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Recipe searchRecipe(String tag) {
		Recipe recipe = Database.searchRecipe(recipeId);
		return recipe;
	}

	@RequestMapping(
		value = "/addrecipe",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Status addRecipe(
		@RequestBody Recipe recipe,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		String userId = Database.getUserIdWithCookie(cookie);
		if(userId == null){
			return new Status(StatusCode.REJECT_NOT_LOGGED_IN);
		}
		String rName = recipe.getRecipe_name();
		String rDesc = recipe.getRecipe_desc();
		if(rName == null || rDesc == null){
			return new Status(StatusCode.RECIPE_NOT_ADDED);
		}
		String rUuid = UUID.randomUUID().toString();
		
		String [] uriList = recipe.getURIs();
		String tag = recipe.getTag();
		
		boolean result = Database.addRecipe(rUuid, rName, rDesc, userId, uriList, tag);
	
		if(result)
			return new Status(StatusCode.RECIPE_ADDED);
		else
			return new Status(StatusCode.RECIPE_NOT_ADDED);
	}
	
	@RequestMapping(
		value = "/deleterecipe",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Status deleteRecipe(
		@RequestBody Recipe recipe,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		String userId = Database.getUserIdWithCookie(cookie);
		if(userId == null){
			return new Status(StatusCode.REJECT_NOT_LOGGED_IN);
		}
		
		String rId = recipe.getRecipe_id();
		if(rId == null){
			return new Status(StatusCode.RECIPE_NOT_DELETED);
		}
		
		boolean result = Database.deleteRecipe(rId, userId);
		if(result)
			return new Status(StatusCode.RECIPE_DELETED);
		else
			return new Status(StatusCode.RECIPE_NOT_DELETED);
	}
	
	@RequestMapping(
		value = "/updaterecipe",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Status updateRecipe(
		@RequestBody Recipe recipe,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		// check if user logged in
		String userId = Database.getUserIdWithCookie(cookie);
		if(userId == null) {
			return new Status(StatusCode.REJECT_NOT_LOGGED_IN);
		}
		
		// check if recipe_id is not null
		String rId = recipe.getRecipe_id();
		if(rId == null) {
			return new Status(StatusCode.RECIPE_NOT_UPDATED);
		}
		
		// check if name attribute is set
		String rName = recipe.getRecipe_name();
		if(rName != null){
			boolean result = Database.updateRecipeName(rId, rName, userId);
			if(!result)
				return new Status(StatusCode.RECIPE_NOT_UPDATED);
		}
		
		// check if desc attribute is set
		String rDesc = recipe.getRecipe_desc();
		if(rDesc != null){
			boolean result = Database.updateRecipeDesc(rId, rDesc, userId);
			if(!result)
				// TODO what if name is updated successfully?
				return new Status(StatusCode.RECIPE_NOT_UPDATED);
		}
		String tag = recipe.getTag();
		if(tag != null) {
			boolean result = Database.updateRecipeTag(rId, tag, userId);
			if(!result)
				return new Status(StatusCode.RECIPE_NOT_UPDATED);
		}
		
		// if everything goes well
		return new Status(StatusCode.RECIPE_UPDATED);
	}
}