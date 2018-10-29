package com.total.Security.utils;

public class CONSTANTS {

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "Zenysh123";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final String USER_ID_AND_TOKEN_MISMATCH = "User id and token mismatch!";
    public static final String USER_ID_NOT_FOUND = "This UserID was not found on System";
    public static final String EMAIL_AND_PASSWORD_MISMATCH = "Email and password mismatch!";
    public static final String USERNAME_AND_PASSWORD_MISMATCH = "Username and password mismatch!";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String ADMIN= "ADMIN";
    public static final String USER = "USER";
    
}
