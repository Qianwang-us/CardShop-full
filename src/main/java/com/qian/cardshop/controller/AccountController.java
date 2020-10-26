package com.qian.cardshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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

/**
 * This controller is used to account management function such as login and register 
 * @author qianwang
 *
 */
@Controller
public class AccountController {
	
	@Autowired
	UserService userService;
	
	/**
	 * When user is registered as customer
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String register(Model model) {
		
		User newUser = new User();
		
		model.addAttribute("newUser", newUser);
		
		return "views/register";
	}
	
	/**
	 * When user wants to be registered as employee
	 * @param isEmployee should be true for employee registeration
	 * @param model
	 * @return
	 */
	@GetMapping("/register/{isEmployee}")
	public String registerEmployee(@PathVariable boolean isEmployee, Model model) {
		
		User newUser = new User();
		
		model.addAttribute("newUser", newUser);
		model.addAttribute("isEmployee", isEmployee);
		
		return "views/register";
	}
	
	/**
	 * This method is called after user complete the registration form and submit, new user will be created
	 * @param newUser user info to save
	 * @param bind for validation check
	 * @param model
	 * @param passcode for employee registration
	 * @return
	 */
	@PostMapping("/register/save")
	public String processRegister(@Valid @ModelAttribute("newUser") User newUser, Errors errors, Model model, @RequestParam(value="passcode", required=false) String passcode) {
		
		if(errors.hasErrors()) {
			return "views/register";
		}
		
		
		if (passcode == null) {
		
		// save the user as customer and related cart is set up
		newUser.setRole("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setCustomer(new Customer());
		userService.save(newUser);
		
		}else {
			if (EmployeeRegister.isValid(passcode)) {
				
				// save the user as employee when passcode is valid
				newUser.setRole("ROLE_ADMIN");
				newUser.setEnabled(true);
				newUser.setEmployee(new Employee());
				userService.save(newUser);
			}else {
				
				// go to register page if the passcode is not valid
				model.addAttribute("message", "You passcode is not valid, please try again.");
				return "views/register";
			}
		}
		
		model.addAttribute("message", "You've registered successfully, please login.");
		
		// once successfully registered, go to login page to indicate user to login with new account
		return "views/login";
	}

	/**
	 * This method is called for login function, configured in spring security
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "views/login";
	}
	
	/**
	 * This method is called when user access some page does not fit his/her role, configured in spring security
	 * @return
	 */
	@GetMapping("/accessdenied")
	public String accessdenied() {
		
		return "views/accessdenied";
	}
}
