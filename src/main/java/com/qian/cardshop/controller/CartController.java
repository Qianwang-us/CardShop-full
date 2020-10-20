package com.qian.cardshop.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qian.cardshop.entity.Cart;
import com.qian.cardshop.entity.Item;
import com.qian.cardshop.entity.User;
import com.qian.cardshop.security.SecurityUtils;
import com.qian.cardshop.service.CartService;
import com.qian.cardshop.service.CustomerService;
import com.qian.cardshop.service.ItemService;
import com.qian.cardshop.service.UserService;
import com.qian.cardshop.util.PaymentSummary;

@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/edit_item")
	public String editItem(@RequestParam("itemId") int itemId, Model model) {

		Optional<Item> tempItem = itemService.findById(itemId);

		if (tempItem.isEmpty()) {
			throw new RuntimeException("Item with id " + itemId + " is not found");
		}
				

		model.addAttribute("item", tempItem.get());

		return "views/product";
	}
	
	@PostMapping("/add_to_cart")
	public String addToCart(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {

		User user = null;
		Cart tempCart = null;
//		try {
//		//User 
//		user = (User)session.getAttribute("currentUser");
//		logger.trace("addToCart, user: " + user);
//		
//		//Cart 
//		tempCart = user.getCustomer().getCart();
//		} catch(Exception e) {
//			redirectAttributes.addAttribute("message", "Your session has expired, please login again.");
//			return "redirect:/login";
//		}

		try {
			String email = SecurityUtils.getUserName();
			
			if(email != null) {
				user = userService.findByEmail(email).get();
				//session.setAttribute("currentUser", user);
				logger.trace("CartController, path(/), user: " + user);
			}
//				User user = userService.findByEmail().get();
//				if(user.getEmail()!=null) {
//					session.setAttribute("currentUser", user);
			}catch(Exception e) {
				//e.printStackTrace();
				logger.error("Cart Controller: Wrong with SecurityUtils.getUserName() ");
			}
		//user = userService.findById(userId).get();
		tempCart = user.getCustomer().getCart();
		
		// edit item, and then add to cart
		if (tempCart.getCartItems().contains(item)) {
			tempCart.updateItem(item);
		} else {
			// view product, and then add new item to cart
			tempCart.addItem(item);
		}

		cartService.save(tempCart);

		logger.trace("Cart: " + tempCart);
		logger.trace("Item: " + tempCart.getCartItems());
		logger.trace("Product: " + item.getProduct());
		

		redirectAttributes.addAttribute("cartId", tempCart.getCartId());

		return "redirect:/view_cart";
	}

	@GetMapping("/view_cart")
	public String viewCart(@RequestParam("cartId") int cartId, Model model) {
		Optional<Cart> cart = cartService.findById(cartId);
		if (cart.isEmpty()) {
			throw new RuntimeException("cart id: "+cartId+" is not found!");
		}
		Cart tempCart = cart.get();
		model.addAttribute("cart", tempCart);
		
		Double itemsTotal = PaymentSummary.calculateItemsTotal(tempCart.getCartItems());
		model.addAttribute("itemsTotal", itemsTotal);

		return "views/cart";
	}

	@GetMapping("/remove_from_cart")
	public String removeFromCart(@RequestParam("itemId") int itemId, RedirectAttributes redirectAttributes) {
		// Optional<Cart> tempCart = cartService.findById(cartId);
		Optional<Item> item = itemService.findById(itemId);
		if (item.isEmpty()) {
			throw new RuntimeException("Item id " + itemId + " is not found!");
		}
		Item tempItem = item.get();
		Cart cart = tempItem.getCart();
		cart.removeItem(tempItem);
		itemService.deleteById(tempItem.getItemId());
		cartService.save(cart);
//		model.addAttribute("cart", cart);
//
//		return "views/cart";
		redirectAttributes.addAttribute("cartId", cart.getCartId());

		return "redirect:/view_cart";
	}
}
