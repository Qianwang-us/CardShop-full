package com.qian.cardshop.util;

public class EmployeeRegister {
	private static final String passcode="admin";
	
	public static boolean isValid(String thePasscode) {
		return thePasscode.equals(passcode);
	}
}
