package com.infinite.jsf.Provider.util;

import java.util.Properties;
import org.mindrot.jbcrypt.BCrypt;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PasswordEncryptor {




	    /**
	     * Hashes a plain-text password using BCrypt.
	     * The generated salt is included in the resulting hash.
	     *
	     * @param plainPassword the user’s password in plain text
	     * @return the BCrypt hash to store in the database
	     */
	    public static String hash(String plainPassword) {
	        // work factor of 12 is a good balance of security and performance
	        String salt = BCrypt.gensalt(12);
	        return BCrypt.hashpw(plainPassword, salt);
	    }

	    /**
	     * Checks whether a plain-text password matches a previously hashed one.
	     *
	     * @param plainPassword the user’s password attempt
	     * @param storedHash    the hash retrieved from the database
	     * @return true if they match, false otherwise
	     */
	    public static boolean verify(String plainPassword, String storedHash) {
	        if (storedHash == null || !storedHash.startsWith("$2a$")) {
	            throw new IllegalArgumentException("Invalid BCrypt hash format");
	        }
	        return BCrypt.checkpw(plainPassword, storedHash);
	    }
	}

	
	
	

