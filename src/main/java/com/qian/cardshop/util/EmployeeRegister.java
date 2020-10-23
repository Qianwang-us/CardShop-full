package com.qian.cardshop.util;

/**
 * This class used for employee registration 
 * 
 * @author qianwang
 *
 */
public class EmployeeRegister {
	private static final String passcode="admin";
	
	/**
	 * Check if it is valid employee registration by matching with passcode
	 * @param thePasscode
	 * @return
	 */
	public static boolean isValid(String thePasscode) {
		return thePasscode.equals(passcode);
	}
}
