package com.total.Security.model;

import java.security.SecureRandom;
import java.util.Random;

public class TokenGenerator {
	protected static SecureRandom random = new SecureRandom();
    
    public synchronized String generateToken(String username ) {
            long longToken = Math.abs(random.nextLong() );
            String random = Long.toString( longToken, 20 );
            return (random);
    }
        public static String getToken() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 22) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length() + 20);
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
