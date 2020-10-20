package com.qian.cardshop.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qian.cardshop.entity.Category;
import com.qian.cardshop.entity.Item;
import com.qian.cardshop.entity.Product;
import com.qian.cardshop.entity.User;
import com.qian.cardshop.exceptions.ProductNotFoundException;
import com.qian.cardshop.security.SecurityUtils;
import com.qian.cardshop.service.CategoryService;
import com.qian.cardshop.service.ProductService;
import com.qian.cardshop.service.UserService;

@Controller
public class HomeController {

	
	@Autowired
	private ProductService productService;	
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	public HomeController(ProductService productService, CustomerService customerService, CartService cartService,
//			ItemService itemService, CategoryService categoryService) {
//		this.productService = productService;
//		this.customerService = customerService;
//		this.cartService = cartService;
//		this.itemService = itemService;
//		this.categoryService = categoryService;
//	}

	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		
		// TODO: need to review!!!!!!!!!!!
		try {
		String email = SecurityUtils.getUserName();
		
		if(email != null) {
			User user = userService.findByEmail(email).get();
			session.setAttribute("currentUser", user);
			logger.trace("HomeController, path(/), user: " + user);
		}
//			User user = userService.findByEmail().get();
//			if(user.getEmail()!=null) {
//				session.setAttribute("currentUser", user);
		}catch(Exception e) {
			//e.printStackTrace();
			System.out.println("Home Controller: Wrong with SecurityUtils.getUserName() ");
		}
		
		List<Product> product = productService.findAll();
		model.addAttribute("products", product);
		return "views/product_list";
	}

//	@GetMapping("/list")
//	//@GetMapping(value = { "/list", "/list/{categoryId}" })
//	public String listProducts(Model model) {
//
//		List<Product> product = productService.findAll();
//		model.addAttribute("products", product);
//		// product list all
//		return "views/product_list";
//	}

	
	@GetMapping("/category/{categoryId}")
	public String browseProductByCategory(@PathVariable int categoryId, Model model) {
		logger.trace("Enter  browseProductByCategory");
		Optional<Category> category = categoryService.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new RuntimeException("no category is found");
		}
		
		Category tempCategory = category.get();
		
		List<Product> product = productService.findByCategory(tempCategory);
	
		logger.trace("product: [" + product+"]");
		
		model.addAttribute("products", product);
		model.addAttribute("categoryName", tempCategory.getCategoryName());
		model.addAttribute("categoryId", categoryId);
		
		logger.trace("Exit  browseProductByCategory");
		return "views/product_list";
	}
	
	@PostMapping("/category")
	public String browseProductByCategory(@RequestParam("sort-type") String sortType, @RequestParam("categoryId") int categoryId, Model model) {
		logger.trace("Enter  browseProductByCategory");
		Optional<Category> category = categoryService.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new RuntimeException("no category is found");
		}
		
		Category tempCategory = category.get();
		
		List<Product> product;
		//= productService.findByCategory(tempCategory);
	
		switch(sortType) {
		case "new":
			product = productService.findByCategoryOrderByCreatedOnDesc(tempCategory);
			break;
		case "priceAsc":
			product = productService.findByCategoryOrderByPriceAsc(tempCategory);
			break;
		case "priceDesc":
			product = productService.findByCategoryOrderByPriceDesc(tempCategory);
			break;
		default:
			product = productService.findByCategoryOrderByCreatedOnDesc(tempCategory);
			break;
		}
		
		model.addAttribute("products", product);
		model.addAttribute("categoryName", tempCategory.getCategoryName());
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("sortType", sortType);
		
		logger.trace("sortType: " + sortType);
		//logger.trace("product: [" + product+"]");
		logger.trace("Exit  browseProductByCategory");
		return "views/product_list";
	}

	@GetMapping("/product/{productId}")
	public String viewProduct(@PathVariable("productId") int productId, Model model) {

		Optional<Product> tempProduct = productService.findById(productId);

		if (tempProduct.isEmpty()) {
			throw new ProductNotFoundException("Product with id " + productId + " is not found");
		}
		Item tempItem = new Item(tempProduct.get());
		model.addAttribute("item", tempItem);

		return "views/product";
	}



	@GetMapping("/about_us")
	public String showAboutUs() {
		return "views/about_us";
	}

}
