package com.squadro.tandir.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
	private StatusCode status;
	
	public Status(StatusCode status){
		this.status = status;
	}
	
	public int getStatus(){
		return status.getStatus();
	}
	
	public String getMessage() {
		return status.getMessage();
	}
}