package com.total.Security.exception;

import org.hibernate.service.spi.ServiceException;

public class RoleNotFoundException extends ServiceException{
	public RoleNotFoundException(String string)
	{
		super(string);
	}
	

}
