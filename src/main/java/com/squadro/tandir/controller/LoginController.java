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
	@ResponseBody
	public Status signinPost(@RequestBody Credential credential) {
		String user = credential.getUser_name();
		String pass = credential.getPassword();

		if(user == null || pass == null) {
			return new Status(StatusCode.SIGNIN_REJECT);
		}
		
		if(Database.checkPassword(user, pass)) {
			return new Status(StatusCode.SIGNIN_SUCCESSFULL);
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
	@ResponseBody
	public Status signupPost(@RequestBody Credential credential) {
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
}