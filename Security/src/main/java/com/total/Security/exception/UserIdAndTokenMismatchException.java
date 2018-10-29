package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

@SuppressWarnings("serial")
public class UserIdAndTokenMismatchException extends ServiceException {
	
	public UserIdAndTokenMismatchException(String message) {
		super(message);
	}


}
