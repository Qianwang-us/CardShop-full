package com.qian.cardshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qian.cardshop.entity.Customer;
import com.qian.cardshop.entity.User;
import com.qian.cardshop.service.UserService;

@Controller
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/register")
	public String register(Model model) {
		
		User newUser = new User();
		
		// container for new user
		model.addAttribute("newUser", newUser);
		
		return "views/register";
	}
	
	@PostMapping("/register/save")
	public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bind, RedirectAttributes redirect) {
		// save the user as customer and related cart is set up
		newUser.setRole("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setCustomer(new Customer());
		userService.save(newUser);
		redirect.addAttribute("message", "You've registered successfully, please login.");
		
		return "redirect:/login";
		
		//return "views/login";
	}

	@GetMapping("/login")
	public String login() {
		return "views/login";
	}
	
	@GetMapping("/accessdenied")
	public String accessdenied() {
		
		return "views/accessdenied";
	}
}
