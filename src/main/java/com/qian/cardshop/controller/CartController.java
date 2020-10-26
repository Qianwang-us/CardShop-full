package com.qian.cardshop.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qian.cardshop.model.Cart;
import com.qian.cardshop.model.Item;
import com.qian.cardshop.model.User;
import com.qian.cardshop.security.SecurityUtils;
import com.qian.cardshop.service.CartService;
import com.qian.cardshop.service.ItemService;
import com.qian.cardshop.service.UserService;
import com.qian.cardshop.util.PaymentSummary;

/**
 * This controller is used for managing items in cart
 * @author qianwang
 *
 */
@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * This method is used to update item info in cart
	 * it is called when user click update of item in cart
	 * @param itemId
	 * @param model
	 * @return
	 */
	@GetMapping("/edit_item")
	public String editItem(@RequestParam("itemId") int itemId, Model model) {

		Optional<Item> tempItem = itemService.findById(itemId);

		if (tempItem.isEmpty()) {
			throw new RuntimeException("Item with id " + itemId + " is not found");
		}
				

		model.addAttribute("item", tempItem.get());

		return "views/product";
	}
	
	/**
	 * This method is used to add new item in the cart
	 * it is called when customer click 'add to cart' button in product page
	 * @param item
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/add_to_cart")
	public String addToCart(@Valid @ModelAttribute("item") Item item, Errors errors, RedirectAttributes redirectAttributes, Model model) {

		if(errors.hasErrors()) {
			model.addAttribute("item", item);
			return "views/product";
		}
		
		User user = null;
		Cart tempCart = null;

		try {
			String email = SecurityUtils.getUserName();
			
			if(email != null) {
				user = userService.findByEmail(email).get();
				logger.trace("CartController, path(/), user: " + user);
			}
				
			}catch(Exception e) {
				logger.error("Cart Controller: Wrong with SecurityUtils.getUserName() ");
			}
		
		tempCart = user.getCustomer().getCart();
		
		// user click update item in cart page, go to product page, and then add to cart
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

	/**
	 * This method is used to show items in cart page
	 * It is called either after customer adding items to cart or click cart icon in the header
	 * 
	 * @param cartId
	 * @param model
	 * @return
	 */
	@GetMapping("/view_cart")
	public String viewCart(@RequestParam(required=false) Integer cartId, Model model) {
		User user = null;
		Cart tempCart = null;
		
		// 
		if(cartId==null) {
			try {
				String email = SecurityUtils.getUserName();
				
				if(email != null) {
					user = userService.findByEmail(email).get();
					logger.trace("CartController, path(/), user: " + user);
				}
					
				}catch(Exception e) {
					logger.error("Cart Controller: Wrong with SecurityUtils.getUserName() ");
				}
			
			tempCart = user.getCustomer().getCart();
		}else {
			Optional<Cart> cart = cartService.findById(cartId);
			if (cart.isEmpty()) {
				throw new RuntimeException("cart id: "+cartId+" is not found!");
			}
			tempCart = cart.get();
		}
		
		
		model.addAttribute("cart", tempCart);
		model.addAttribute("empty", (tempCart.getCartItems().size()==0));
		
		// calculate payment summary for cart page
		Double itemsTotal = PaymentSummary.calculateItemsTotal(tempCart.getCartItems());
		model.addAttribute("itemsTotal", itemsTotal);

		return "views/cart";
	}

	/**
	 * This method is used to remove items from cart
	 * It is called when customer click delete in cart page
	 * @param itemId
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/remove_from_cart")
	public String removeFromCart(@RequestParam("itemId") int itemId, RedirectAttributes redirectAttributes) {
		Optional<Item> item = itemService.findById(itemId);
		if (item.isEmpty()) {
			throw new RuntimeException("Item id " + itemId + " is not found!");
		}
		Item tempItem = item.get();
		Cart cart = tempItem.getCart();
		cart.removeItem(tempItem);
		itemService.deleteById(tempItem.getItemId());
		cartService.save(cart);
		
		redirectAttributes.addAttribute("cartId", cart.getCartId());

		return "redirect:/view_cart";
	}
}
