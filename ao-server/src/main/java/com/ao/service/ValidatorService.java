

package com.ao.service;

import org.apache.commons.validator.GenericValidator;

/**
 * Provides validation services.
 */
public class ValidatorService {

	private static final int CHARACTER_NAME_MIN_LENGTH = 1;
	private static final int CHARACTER_NAME_MAX_LENGTH = 30;
	private static final String CHARACTER_NAME_REGEXP = "^[A-Za-z][A-Za-z ]*";

	/**
	 * Checks if the given character name is valid, or not.
	 * 
	 * @param name The character name.
	 * @return True if the name is valid, false otherwise.
	 */
	public static boolean validCharacterName(String name) {
		//TODO: Take this to the security modules.
		boolean res = true;
		
		res = res && GenericValidator.matchRegexp(name, CHARACTER_NAME_REGEXP);
		res = res && GenericValidator.isInRange(name.length(), CHARACTER_NAME_MIN_LENGTH, CHARACTER_NAME_MAX_LENGTH);
		
		return res;
	}
	
	/**
	 * Checks if the given email address is valid, or not.
	 * @param email The email address.
	 * @return True if the email is valid, false otherwise.
	 */
	public static boolean validEmail(String email) {
		return GenericValidator.isEmail(email);
	}
}
