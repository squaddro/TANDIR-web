package com.squadro.tandir.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

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
import com.squadro.tandir.message.Credential;
import com.squadro.tandir.service.Database;

@RestController
public class LoginController {
	
	@RequestMapping(
		value = "/signin",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
		
	)
	public Status signinPost(
		@RequestBody Credential credential,
		HttpServletResponse response,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		if(Database.getUserIdWithCookie(cookie) != null){
			return new Status(StatusCode.REJECT_ALLREADY_LOGGED_IN);
		}
		
		String user = credential.getUser_name();
		String pass = credential.getPassword();
		String token = credential.getToken();
		
		
		
		if(user == null || pass == null) {
			return new Status(StatusCode.SIGNIN_REJECT);
		}
		Database.setToken(user, token);
		if(Database.checkPassword(user, pass)) {
			String newUuid = UUID.randomUUID().toString();
			if(Database.setCookie(user, newUuid)){
				response.addCookie(new Cookie("cookie_uuid", newUuid));
				return new Status(StatusCode.SIGNIN_SUCCESSFULL);
			}
			else
				return new Status(StatusCode.SIGNIN_REJECT_COOKIE_NOTSET);
		}
		else {
			return new Status(StatusCode.SIGNIN_REJECT);
		}
	}
	
	@RequestMapping(
		value = "/signup",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public Status signupPost(
		@RequestBody Credential credential,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		if(Database.getUserIdWithCookie(cookie) != null){
			return new Status(StatusCode.REJECT_ALLREADY_LOGGED_IN);
		}
		
		String user = credential.getUser_name();
		String pass = credential.getPassword();

		if(user == null || pass == null) {
			return new Status(StatusCode.SIGNUP_REJECT);
		}
		
		if(Database.addUser(user, pass)) {
			return new Status(StatusCode.SIGNUP_SUCCESSFULL);
		}
		else {
			return new Status(StatusCode.SIGNUP_REJECT);
		}
	}
	
	@RequestMapping(
		value = "/signout",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Status signoutGet(
		HttpServletResponse response,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		Cookie cookieToRemove = new Cookie("cookie_uuid", "signedout");
		cookieToRemove.setMaxAge(0);
		response.addCookie(cookieToRemove);
		String userId = Database.getUserIdWithCookie(cookie);
		if(userId == null){
			return new Status(StatusCode.REJECT_NOT_LOGGED_IN);
		}
		
		Database.setCookie(userId, null); // set user cookie to null
		return new Status(StatusCode.SIGNOUT_SUCCESSFULL);
	}
}