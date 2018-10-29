package com.total.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.total.Security.request.ImageRequest;
import com.total.Security.service.ImageService;

@RestController
@RequestMapping("/rest/upload")
public class upload_pictureController {
	
	@Autowired
	private ImageService service; 

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> postAirports(@RequestBody ImageRequest request)
	{
	service.setImages(request.getImage());
	return new ResponseEntity<Object>("uploaded", HttpStatus.OK);
	
	}
}
