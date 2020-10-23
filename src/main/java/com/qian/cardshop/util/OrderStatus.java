package com.qian.cardshop.util;

/**
 * This is class is used to get next processing status of the order based on:
 * CREATED > INPROCESS > SHIPPED > COMPLETE (currently cancelled function is not
 * supported yet)
 * 
 * @author qianwang
 *
 */
public class OrderStatus {
	
	/**
	 * This method help to change order status to next valid one
	 * 
	 * @param originalStatus
	 * @return
	 */
	public static String getNextStatus(String originalStatus) {
		String nextStatus = null;
		switch (originalStatus) {
		case "CREATED":
			nextStatus = "INPROCESS";
			break;
		case "INPROCESS":
			nextStatus = "SHIPPED";
			break;
		case "SHIPPED":
			nextStatus = "COMPLETE";
			break;
		case "COMPLETE":
			nextStatus = "COMPLETE";
			break;
		default:
			nextStatus = null;
			break;
		}
		
		return nextStatus;
	}
	
	
	/**
	 * This method help to provide next step operation name to show up in order_fulfilment.html page
	 * 
	 * @param originalStatus
	 * @return
	 */
	public static String getNextStep(String originalStatus) {
		String nextStep = null;
		switch (originalStatus) {
		case "CREATED":
			nextStep = "Process Orders";
			break;
		case "INPROCESS":
			nextStep = "Ship Orders";
			break;
		case "SHIPPED":
			nextStep = "Complete Orders";
			break;
		case "COMPLETE":
			nextStep = "Complete Orders";
			break;
		default:
			nextStep = null;
			break;
		}
		
		return nextStep;
	}
}
