package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

@SuppressWarnings("serial")
public class TokenHasExpiredException extends ServiceException {

	public TokenHasExpiredException(String message)
	{
		super(message);
	}
}
