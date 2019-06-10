package com.squadro.tandir.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import com.squadro.tandir.message.Recipe;
import com.squadro.tandir.message.Status;
import com.squadro.tandir.message.StatusCode;

@RestController
public class RecipeController {
	
	Recipe temp;
	
	@RequestMapping(
		value = "/recipe",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Recipe[] recipe() {
		// TODO DATABASE
		Recipe[] recipes = new Recipe[100];
		for(int i=0; i<100; i++) {
			recipes[i] = randomRecipe();
		}
		return recipes;
	}
	
	@RequestMapping(
		value = "/recipe/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Recipe recipeWithId(@PathVariable("id") String recipeId) {
		// TODO DATABASE
		return temp;
	}
	
	@RequestMapping(
		value = "/addrecipe",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Status addRecipe(@RequestBody Recipe recipe) {
		// TODO DATABASE
		temp = recipe;
		return new Status(StatusCode.RECIPE_ADDED);
	}
	
	private Recipe randomRecipe(){
		String id = UUID.randomUUID().toString();
		String name = UUID.randomUUID().toString();
		String desc = UUID.randomUUID().toString();
		String user = UUID.randomUUID().toString();
		return new Recipe(id, name, desc, user);
	}
}