package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;


public class InvalidJwtAuthenticationException extends ServiceException{
	
	public InvalidJwtAuthenticationException(String string)
	{
		super(string);
	}

}
