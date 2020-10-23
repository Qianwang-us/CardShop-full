package com.qian.cardshop.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qian.cardshop.model.Cart;
import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.Order;
import com.qian.cardshop.service.OrderService;
import com.qian.cardshop.util.OrderStatus;

/**
 * This controller used by admin for the purpose of order fulfillment
 * @author qianwang
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	OrderService orderService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
//	/**
//	 * default page when admin clicks on admin icon from header
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@GetMapping("/order_fulfillment")
//	public String fulfillOrder(RedirectAttributes redirectAttributes) {
//		
//		redirectAttributes.addAttribute("order_status", "CREATED");
//		return "redirect:/admin/orders";
//	}
	
	/**
	 * this method return order list by order status and set proper name for next step
	 * @param orderStatus
	 * @param model
	 * @return
	 */
	@GetMapping("/orders")
	public String getOrdersByStatus(@RequestParam(value="order_status", defaultValue="CREATED", required=false) String orderStatus, Model model) {
		List<Order> orders = orderService.findByOrderStatus(orderStatus);
		
		model.addAttribute("orders", orders);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("nextStep", OrderStatus.getNextStep(orderStatus));
		
		logger.trace("getOrdersByStatus, nextStep: " + OrderStatus.getNextStep(orderStatus));
		
		return "views/order_fulfillment";
	}
	
	/**
	 * this method updates order status to the next, after admin clicks on 'next step' in order_fulfillment.html
	 * @param orderStatus
	 * @param model
	 * @return
	 */
	@GetMapping("/update_status")
	public String updateOrderStatus(@RequestParam("orderStatus") String orderStatus, @RequestParam(value="orderIdList", required=false) Integer[] orderIdList, Model model) {
		//TODO: update order status
		
		if(orderIdList != null && orderIdList.length > 0) {
			
			Set<Integer> idSet = new HashSet<Integer>(Arrays.asList(orderIdList));
			List<Order> orders = orderService.findByOrderIds(idSet);
			orders.forEach(order->order.setOrderStatus(OrderStatus.getNextStatus(orderStatus)));
			orders.forEach(order->orderService.save(order));
		}

			
		logger.trace("updateOrderStatus, order Id list: "+ Arrays.toString(orderIdList));
		
		return "redirect:/admin/orders";
	}
	
	/**
	 * used for admin to quick check an order with orderId, in order_fulfillment.html page
	 * @param orderId
	 * @param model
	 * @return
	 */
	@GetMapping("/view_order")
	public String viewOrder(@RequestParam("orderId") int orderId, Model model) {
		Order order = null;
		
		try {
			order = orderService.findById(orderId).get();
		}catch(Exception e) {
			String errorMsg = "No order is found!";
			logger.error(errorMsg);
			model.addAttribute("error", errorMsg);
			return "views/error";
		}
		
		
		model.addAttribute("order", order);
		// pass the order to view, show details
		return "views/order";
	}
}
