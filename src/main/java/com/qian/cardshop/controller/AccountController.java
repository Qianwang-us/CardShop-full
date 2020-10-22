package com.qian.cardshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.Employee;
import com.qian.cardshop.model.User;
import com.qian.cardshop.service.UserService;
import com.qian.cardshop.util.EmployeeRegister;

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
	
	@GetMapping("/register/{isEmployee}")
	public String registerEmployee(@PathVariable boolean isEmployee, Model model) {
		
		User newUser = new User();
		
		// container for new user
		model.addAttribute("newUser", newUser);
		model.addAttribute("isEmployee", isEmployee);
		
		return "views/register";
	}
	
	@PostMapping("/register/save")
	public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bind, Model model, @RequestParam(value="passcode", required=false) String passcode) {
		
		if (passcode == null) {
		
		// save the user as customer and related cart is set up
		newUser.setRole("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setCustomer(new Customer());
		userService.save(newUser);
		
		}else {
			if (EmployeeRegister.isValid(passcode)) {
				newUser.setRole("ROLE_ADMIN");
				newUser.setEnabled(true);
				newUser.setEmployee(new Employee());
				userService.save(newUser);
			}else {
				model.addAttribute("message", "You passcode is not valid, please try again.");
				return "views/register";
			}
		}
		
		model.addAttribute("message", "You've registered successfully, please login.");
		
		//return "redirect:/login";
		
		return "views/login";
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
