package com.squadro.tandir.message;

public class Greeting {
	private final long id;
	private final String content;
	private final Joke joke;
	
	public Greeting(long id, String content, Joke joke) {
		this.id = id;
		this.content = content;
		this.joke = joke;
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public Joke getJoke() {
		return joke;
	}
}