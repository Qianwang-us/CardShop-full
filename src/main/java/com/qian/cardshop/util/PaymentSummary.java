package com.qian.cardshop.util;

import java.util.ArrayList;
import java.util.List;

import com.qian.cardshop.model.Item;

/**
 * this class helps to calculate payment summary information for cart and order detail page
 * @author qianwang
 *
 */
public class PaymentSummary {

	public static final Double taxRate = 0.1;
	public static final Double shipping = 3.99;
	
	/**
	 * This method helps to calculate payment summary for order
	 * @param itemList
	 * @return
	 */
	public static ArrayList<Double> calculatePayment(List<Item> itemList) {
		double itemsTotal = 0.0;
		
		for(Item item: itemList) {
			Double price = item.getProduct().getPrice();
			itemsTotal += price*item.getQuantity();
		}
			
		double tax = itemsTotal*taxRate;
		
		itemsTotal = (double)Math.round(itemsTotal*100)/100;
		tax = (double)Math.round(tax*100)/100;
	
		double orderTotal = itemsTotal + shipping + tax;
		orderTotal = (double)Math.round(orderTotal*100)/100;
		
		ArrayList<Double> payment = new ArrayList<>();
		
		payment.add(itemsTotal);
		payment.add(shipping);
		payment.add(tax);
		payment.add(orderTotal);
		
		return payment;
	}
	
	/**
	 * This method helps to calculate payment for cart
	 * @param itemList
	 * @return
	 */
	public static Double calculateItemsTotal(List<Item> itemList) {
		Double itemsTotal = 0.0;
		
		for(Item item: itemList) {
			double price = item.getProduct().getPrice();
			itemsTotal += price*item.getQuantity();
		}
		
		
		itemsTotal =  (double)Math.round(itemsTotal*100)/100;
		
		return itemsTotal;
	}
	
}


