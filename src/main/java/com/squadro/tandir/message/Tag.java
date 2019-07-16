package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

	private String tag;
	
	public Tag(){ }
	
	public Tag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
}