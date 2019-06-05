package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {
	
	private String id;
	private String joke;
	private int status;
	
	public Joke(){
		
	}
	
	public Joke(String id, String joke, int status) {
		this.id = id;
		this.joke = joke;
		this.status = status;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setJoke(String joke) {
		this.joke = joke;
	}
	
	public String getJoke() {
		return joke;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}