package com.qian.cardshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/order_fulfillment")
	public String fulfillOrder() {
		return "views/order_fulfillment";
	}
}
