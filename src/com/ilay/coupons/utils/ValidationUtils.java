package com.ilay.coupons.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
	
	
	public boolean isEmailAddressValid(String emailAddress) { 	//checks for email validation.
		
		String emailRegex =  "^[a-zA-Z0-9_+&*-]+(?:\\."+		//Regex expiration for valid email address.
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
		
		Pattern pattern = Pattern.compile(emailRegex);	// convert the Regex expiration into pattern for matching.
		
		if(emailAddress == null) {		//if email Address is empty return false.
			return false;
		}else {
			return pattern.matcher(emailAddress).matches();		//match the pattern with the given email  address: returns true/false.
		}
		
	}
	
	public boolean ispasswordValid (String password) {	//set password rules and validate them.
		
		final Pattern hasUppercase = Pattern.compile("[A-Z]");		//checks for upper case letters.
		final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");	//checks for any other char.
		
		if(password == null) {		//password can't be null.
			return false;
		}
		if(password.isEmpty()) {		//password can't be empty.
			return false;
		}
		if(password.length()<=8) {		//password must contain more than 8 characters.
			return false;
		}
		if(!hasUppercase.matcher(password).find()) {	//password must have at least one upper case.
			return false;
		}
		if(hasSpecialChar.matcher(password).find()) {	//password can't contain special characters (!,@,#,).
			return false;
		}
		
		return true;	//return true when password is valid.
	}
	

}
