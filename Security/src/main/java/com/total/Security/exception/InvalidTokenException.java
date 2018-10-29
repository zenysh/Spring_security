package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

public class InvalidTokenException extends ServiceException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2704064261731198242L;

	public InvalidTokenException(String string)
	{
		super(string);
	}
	

}
