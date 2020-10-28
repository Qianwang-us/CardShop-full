package com.qianwang.cardshop.exception;

public class ProductNotFoundException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8260149297090830111L;
	public ProductNotFoundException() {}
	public ProductNotFoundException(String errorMessage) {
		// TODO Auto-generated constructor stub
		super(errorMessage);
	}

}
