package com.qian.cardshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qian.cardshop.model.Cart;
import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.CustomerDetail;
import com.qian.cardshop.model.Item;
import com.qian.cardshop.model.Order;
import com.qian.cardshop.model.Receiver;
import com.qian.cardshop.service.CartService;
import com.qian.cardshop.service.CustomerDetailService;
import com.qian.cardshop.service.ItemService;
import com.qian.cardshop.service.OrderService;
import com.qian.cardshop.util.PaymentSummary;

/**
 * Controller which contains methods in checkout process and order related functions
 * 
 * @author qianwang
 *
 */

@Controller
@RequestMapping("/order")
public class OrderController {
	
	OrderService orderService;
	CartService cartService;
	CustomerDetailService customerDetailService;
	ItemService itemService;
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public OrderController(OrderService orderService, CartService cartService, CustomerDetailService customerDetailService, ItemService itemService) {
		this.orderService = orderService;
		this.cartService = cartService;
		this.customerDetailService = customerDetailService;
		this.itemService = itemService;
	}
	
	/**
	 * part of checkout process. After customer viewed the cart, user go to shipping address page for order delivering through this method
	 * 
	 * @param cartId
	 * @param model
	 * @return
	 */
	@GetMapping("/shipping_address")
	public String addShippingAddress(@RequestParam("cartId") int cartId, Model model) {
		CustomerDetail customerDetail = null;
		Customer customer = null;
		String customerName="";
		try {
			customer = cartService.findById(cartId).get().getCustomer();
			customerName = customer.getUser().getFirstName() +" "+ customer.getUser().getLastName();
			customerDetail = customer.getCustomerDetail();
					
		}catch(Exception e) {
			String errorMsg = "no cart or customer is found!";
			logger.error(errorMsg);
			model.addAttribute("error", errorMsg);
			return "views/error";
			//throw new RuntimeException("errorMsg");
		}
		
		Receiver receiver = new Receiver();
		String billingAddressType="CUSTOMER";
		
		
		model.addAttribute("cartId", cartId);
		model.addAttribute("customerName", customerName);
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("receiver", receiver);
		model.addAttribute("billingAddressType", billingAddressType);
		
		return "views/shipping_address";
		
	}
	
	
	/**
	 * part of checkout process, use this method when user decides to use current address stored in customer details as order delivery address
	 * copy the address in customer details to receiver and pass it on to view
	 * calculate payment info and pass it on to view
	 * 
	 * @param cartId
	 * @param model
	 * @return
	 */
	@GetMapping("/checkout")
	public String checkOutWithCurrentAddress(@RequestParam("cartId") int cartId, Model model) {
		logger.trace("Enter method: checkOutWithCurrentAddress");
		
		// retrieve address in customer details and save it to receiver
		
		Customer customer = null;
		Cart cart = null;
		
		try {
			cart = cartService.findById(cartId).get();
			customer = cart.getCustomer();
					
		}catch(Exception e) {
			String errorMsg = "no cart or customer is found!";
			logger.error(errorMsg);
			model.addAttribute("error", errorMsg);
			return "views/error";
			//throw new RuntimeException("errorMsg");
		}
		
		Receiver receiver = new Receiver(customer);
		
		
		// save these info below to view which will be used for order creation
		model.addAttribute("receiver", receiver);
		model.addAttribute("cartId", cartId);
		model.addAttribute("cart", cart);
		model.addAttribute("billingAddressType", "CUSTOMER");
		
		//calculate payment info and pass it on to view	
		ArrayList<Double> payment = PaymentSummary.calculatePayment(cart.getCartItems());
		model.addAttribute("itemsTotal", payment.get(0));
		model.addAttribute("shipping", payment.get(1));
		model.addAttribute("tax", payment.get(2));
		model.addAttribute("orderTotal", payment.get(3));
		
		logger.trace("receiver: "+ receiver);
		logger.trace("cartId: "+ cartId);		
		logger.trace("Exit method: checkOutWithCurrentAddress");
		
		return "views/checkout";
	}
	
	/**
	 * part of checkout process, use this method when user decides to use new entered address as order delivery address. 
	 * if user checked the save to personal address checkbox, the address is saved to customer details and will override the old one if it exists
	 * calculate payment info and pass it on to view
	 * 
	 * @param receiver
	 * @param cartId
	 * @param billingAddressType
	 * @param save
	 * @param model
	 * @return
	 */
	@PostMapping("/checkout")
	public String checkOutWithNewAddress(@ModelAttribute("receiver") Receiver receiver, @RequestParam("cartId") int cartId, @RequestParam("billingAddressType") String billingAddressType, @RequestParam("save") boolean save, Model model) {
		logger.trace("Enter method: checkOutWithNewAddress");
		
		//retrieve cart information for getting customer later on
		Cart cart = null;
		
		try {
			cart = cartService.findById(cartId).get();			
								
		}catch(Exception e) {
			String errorMsg = "no cart is found!";
			logger.error(errorMsg);
			model.addAttribute("error", errorMsg);
			return "views/error";
			//throw new RuntimeException("errorMsg");
		}
		
		
		
		// save the new address as customer personal address		
		if(save) {
			CustomerDetail customerDetail = null;
			Customer customer = null;
			
			try {
				customer = cart.getCustomer();			
				customerDetail = customer.getCustomerDetail();
						
			}catch(Exception e) {
				String errorMsg = "no cart or customer is found!";
				logger.error(errorMsg);
				model.addAttribute("error", errorMsg);
				return "views/error";
				//throw new RuntimeException("errorMsg");
			}
			
			if (customerDetail == null) {
				customerDetail = new CustomerDetail(receiver);	
				customer.setCustomerDetail(customerDetail);
			}else {
				customerDetail.update(receiver);
			}
			
			customerDetailService.save(customerDetail);
			logger.trace("customerDetail: "+ customerDetail);
		}
		
		// save these info below to view which will be used for order creation
		model.addAttribute("receiver", receiver);
		model.addAttribute("cartId", cartId);
		model.addAttribute("cart", cart);
		model.addAttribute("billingAddressType", billingAddressType);
		
		//calculate payment info and pass it on to view	
		ArrayList<Double> payment = PaymentSummary.calculatePayment(cart.getCartItems());
		model.addAttribute("itemsTotal", payment.get(0));
		model.addAttribute("shipping", payment.get(1));
		model.addAttribute("tax", payment.get(2));
		model.addAttribute("orderTotal", payment.get(3));
		
		logger.trace("receiver: "+ receiver);
		logger.trace("cartId: "+ cartId);
		logger.trace("billingAddressType: "+ billingAddressType);
		logger.trace("save: "+ save);
		logger.trace("Exit method: checkOutWithNewAddress");
		
		return "views/checkout";
	}
	
	/**
	 * 
	 * Create order with cart items and return order confirmation with order id and created date. Clear items in the cart.
	 * 
	 * @param receiver
	 * @param cartId
	 * @param billingAddressType
	 * @param itemsTotal
	 * @param shipping
	 * @param tax
	 * @param orderTotal
	 * @param model
	 * @return
	 */
	@PostMapping("/create_order")
	public String createOrder(@ModelAttribute("receiver") Receiver receiver, 
			@RequestParam("cartId") int cartId,
			@RequestParam("billingAddressType") String billingAddressType,
			@RequestParam("itemsTotal") double itemsTotal,
			@RequestParam("shipping") double shipping,
			@RequestParam("tax") double tax,
			@RequestParam("orderTotal") double orderTotal,
			Model model) {
		
		logger.trace("Enter method: createOrder");
		
		logger.trace("receiver: "+ receiver);
		logger.trace("cartId: "+ cartId);
		logger.trace("billingAddressType: "+ billingAddressType);
		logger.trace("itemsTotal: "+ itemsTotal);
		logger.trace("shipping: "+ shipping);
		logger.trace("tax: "+ tax);
		logger.trace("orderTotal: "+ orderTotal);
		
		Cart cart = null;
		Customer customer = null;
		try {
			cart = cartService.findById(cartId).get();	
			customer = cart.getCustomer();
								
		}catch(Exception e) {
			String errorMsg = "no cart is found!";
			logger.error(errorMsg);
			model.addAttribute("error", errorMsg);
			return "views/error";
			//throw new RuntimeException("errorMsg");
		}
		
		// create order
		Order tempOrder = new Order(customer, receiver, billingAddressType, itemsTotal, shipping, tax, orderTotal);				
		List<Item> items = cart.getCartItems();
		
		// clear the cart first to avoid shared collection exception
		cart.clear();
		cartService.save(cart);
		orderService.save(tempOrder);
		tempOrder.setOrderItems(items);
		// update and save item with new order id info
		items.forEach(item -> itemService.save(item));
		
		
		// pass order id and created date to view
		model.addAttribute("orderId", tempOrder.getOrderId());
		model.addAttribute("createdOn", tempOrder.getCreatedOn());
		
		logger.trace("orderId: " + tempOrder.getOrderId());
		logger.trace("createdOn: " + tempOrder.getCreatedOn());
		logger.trace("order: " + tempOrder);
		
		logger.trace("Exit method: createOrder");
		return "views/order_confirm";
	}
	
	/**
	 * show order history of the customer when customer clicks 'orders' in the header
	 * @return
	 */
	@GetMapping("/order_history")
	public String showOrderHistory() {
		return "views/order_history";
	}

}
