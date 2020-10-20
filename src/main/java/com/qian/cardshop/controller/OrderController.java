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

import com.qian.cardshop.entity.Cart;
import com.qian.cardshop.entity.Customer;
import com.qian.cardshop.entity.CustomerDetail;
import com.qian.cardshop.entity.Item;
import com.qian.cardshop.entity.Order;
import com.qian.cardshop.entity.Receiver;
import com.qian.cardshop.service.CartService;
import com.qian.cardshop.service.CustomerDetailService;
import com.qian.cardshop.service.ItemService;
import com.qian.cardshop.service.OrderService;
import com.qian.cardshop.util.PaymentSummary;



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
	
	
	
	@GetMapping("/checkout")
	public String checkOutWithCurrentAddress(@RequestParam("cartId") int cartId, Model model) {
		logger.trace("Enter method: checkOutWithCurrentAddress");
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
		
		model.addAttribute("receiver", receiver);
		model.addAttribute("cartId", cartId);
		model.addAttribute("cart", cart);
		model.addAttribute("billingAddressType", "CUSTOMER");
		
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
	
	@PostMapping("/checkout")
	public String checkOutWithNewAddress(@ModelAttribute("receiver") Receiver receiver, @RequestParam("cartId") int cartId, @RequestParam("billingAddressType") String billingAddressType, @RequestParam("save") boolean save, Model model) {
		logger.trace("Enter method: checkOutWithNewAddress");
		
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
		
		model.addAttribute("receiver", receiver);
		model.addAttribute("cartId", cartId);
		model.addAttribute("cart", cart);
		model.addAttribute("billingAddressType", billingAddressType);
		
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
	
	@PostMapping("/create_order")
	public String createOrder(@ModelAttribute("receiver") Receiver receiver, 
			//@ModelAttribute("cart") Cart cart, 
			@RequestParam("cartId") int cartId,
			//@ModelAttribute("billingAddressType") String billingAddressType, 
			@RequestParam("billingAddressType") String billingAddressType,
			@RequestParam("itemsTotal") double itemsTotal,
			@RequestParam("shipping") double shipping,
			@RequestParam("tax") double tax,
			@RequestParam("orderTotal") double orderTotal,
			//@ModelAttribute("itemsTotal") Double itemsTotal, @ModelAttribute("shipping") Double shipping, 
			//@ModelAttribute("tax") Double tax, @ModelAttribute("orderTotal") Double orderTotal,
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
		
		Order tempOrder = new Order(customer, receiver, billingAddressType, itemsTotal, shipping, tax, orderTotal);				
		List<Item> items = cart.getCartItems();
		cart.clear();
		cartService.save(cart);
		orderService.save(tempOrder);
		tempOrder.setOrderItems(items);
		items.forEach(item -> itemService.save(item));
		
		model.addAttribute("orderId", tempOrder.getOrderId());
		model.addAttribute("createdOn", tempOrder.getCreatedOn());
		
		logger.trace("orderId: " + tempOrder.getOrderId());
		logger.trace("createdOn: " + tempOrder.getCreatedOn());
		logger.trace("order: " + tempOrder);
		
		logger.trace("Exit method: createOrder");
		return "views/order_confirm";
	}
	
	@GetMapping("/order_history")
	public String showOrderHistory() {
		return "views/order_history";
	}

}
