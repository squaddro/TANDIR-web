package com.squadro.tandir.controller;

import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.squadro.tandir.message.Like;
import com.squadro.tandir.message.Recipe;
import com.squadro.tandir.message.Status;
import com.squadro.tandir.message.StatusCode;
import com.squadro.tandir.message.Tag;
import com.squadro.tandir.service.Database;

@RestController
public class FcmController {
	
	@RequestMapping(
			value = "/like",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
		@ResponseBody
		public Status likeRecipe(
		@RequestBody Like like,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
		) throws JSONException {
			String recipe_id = like.getRecipe_id();
			String token = Database.getToken(recipe_id);
			Recipe recipe = Database.getRecipe(recipe_id);
			String user_name = Database.getUserIdWithCookie(cookie);
			String recipe_name = recipe.getRecipe_name();
			
			postToFcm(token,user_name,recipe_name);
			return new Status(StatusCode.LIKE_SUCCESSFULL);
		}
	
	public void postToFcm(String token,String user_name,String recipe_name) throws JSONException {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "key=AAAA0IcIHCE:APA91bH0xyuHY5IDom5oLiZ1Uno4i4zpHk3fzAy6U3JiOTbbtGfvbn4BlKm6vOfb9r1Tq7qVrTXk9JKcDUjVlE_NAEUKwyLHYRKzyWcq0QTTg4ncmXybJny1NYQxhpAZ87Hs7TH5lN1b");
		
		JSONObject obj = new JSONObject();
		obj.put("to", token);
		
		JSONObject innerObj = new JSONObject();
		innerObj.put("title", "TANDIR");
		innerObj.put("body","Your recipe named "+recipe_name+" has been liked by user "+ user_name );
		obj.put("notification", innerObj);
		
		
		String url = "https://fcm.googleapis.com/fcm/send";
		
		HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
		restTemplate.postForObject(url, request, String.class);
		
	}

}
