package com.total.Security.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping(path="hello-internationalized")
	public String hello(@RequestHeader(name="Accept-Language", required=false)Locale locale)
	{
		return messageSource.getMessage("hello.world.message", null, locale);
	}

}
 