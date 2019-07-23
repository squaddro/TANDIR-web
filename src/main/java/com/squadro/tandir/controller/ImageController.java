package com.squadro.tandir.controller;

import java.util.UUID;
import java.util.Base64;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

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
import com.squadro.tandir.message.Tag;
import com.squadro.tandir.message.Payload;
import com.squadro.tandir.service.Database;


@RestController
public class ImageController {
	
	@RequestMapping(
		value = "/image/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Payload getImage(@PathVariable("id") String imageId) {
		byte[] bytes = null;
		bytes = Database.getImage(imageId);//Base64.getDecoder().decode(base64png.getBytes("UTF-8"));
		Payload payload = new Payload();
		payload.setPayload(Base64.getEncoder().encodeToString(bytes));
		return payload;
	}
	
	@RequestMapping(
		value = "/imagebin/{id}",
		method = RequestMethod.GET,
		produces = MediaType.IMAGE_JPEG_VALUE
	)
	public byte[] getImageBin(@PathVariable("id") String imageId) {
		byte[] bytes = null;
		return Database.getImage(imageId);//Base64.getDecoder().decode(base64png.getBytes("UTF-8"));
	}
	
	@RequestMapping(
		value = "/upload",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseBody
	public Status uploadImage(
		@RequestBody Payload payload,
		@CookieValue(value = "cookie_uuid", defaultValue = "notset") String cookie
	) {
		String rUuid = UUID.randomUUID().toString();
		byte[] bytes = null;
		try{
			bytes = Base64.getDecoder().decode(payload.getPayload().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Status status = new Status(StatusCode.UPLOAD_FAILED);
			status.setMessage("debug1");
			return status;
		}
		
		boolean result = Database.addImage(rUuid, bytes);
	
		if(result){
			Status status = new Status(StatusCode.UPLOAD_SUCCESSFULL);
			status.setMessage(rUuid);
			return status;
		}
		else{
			Status status = new Status(StatusCode.UPLOAD_FAILED);
			status.setMessage("debug2");
			return status;
		}
	}
}