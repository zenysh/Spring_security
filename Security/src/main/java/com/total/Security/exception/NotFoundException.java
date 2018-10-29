package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

public class NotFoundException extends ServiceException{
	public NotFoundException(String string) {
		super(string);
	}

}
