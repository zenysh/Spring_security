package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

public class AlreadyExistException extends ServiceException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistException(String string) {
		super(string);
	}

}
