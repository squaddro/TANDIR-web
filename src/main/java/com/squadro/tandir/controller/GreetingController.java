package com.squadro.tandir.controller;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import com.squadro.tandir.message.Greeting;
import com.squadro.tandir.message.Joke;

@RestController
public class GreetingController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
	public Greeting greeting(@RequestParam(value="name", defaultValue="Mark") String name) {
		Joke joke = getTheJoke();
		String content = "Oh hi, " + name + "! Here is your joke as a json object!";
		return new Greeting(counter.incrementAndGet(), content, joke);
	}
	
	@RequestMapping(value = "/greeting", produces = MediaType.TEXT_PLAIN_VALUE)
	public String greetingTextPlain(@RequestParam(value="name", defaultValue="Mark") String name) {
		Joke joke = getTheJoke();
		return "Oh hi " + name + "!. Here is you joke as a text: \'" + joke.getJoke() + "\'";
	}
	
	@RequestMapping(value = "/greeting", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView greetingPage(@RequestParam(value="name", defaultValue="Mark") String name) {
		Joke joke = getTheJoke();
		
		// generates from resource/templates/greeting.html
		ModelAndView model = new ModelAndView("greeting"); 
		
		model.addObject("name", name);
		model.addObject("joke", joke.getJoke());
		return model;
	}
	
	/**
		returns joke from www.icanhazdadjoke.com
	*/
	private Joke getTheJoke() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "my lib https://github.com/squaddro/tandir-web");
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Joke> response = restTemplate.exchange(
			"https://www.icanhazdadjoke.com/",
			HttpMethod.GET,
			new HttpEntity("parameters", headers),
			Joke.class
		);
		
		return response.getBody();
	}
}