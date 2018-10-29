package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

@SuppressWarnings("serial")
public class UnauthorizedException extends ServiceException{
	public UnauthorizedException(String message) {
		super(message);
	}

}
