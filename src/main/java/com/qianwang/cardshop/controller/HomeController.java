package com.qianwang.cardshop.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qianwang.cardshop.exception.ProductNotFoundException;
import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.Item;
import com.qianwang.cardshop.model.Product;
import com.qianwang.cardshop.model.User;
import com.qianwang.cardshop.security.SecurityUtils;
import com.qianwang.cardshop.service.CategoryService;
import com.qianwang.cardshop.service.ProductService;
import com.qianwang.cardshop.service.UserService;
import com.qianwang.cardshop.util.ProductList;

@Controller
public class HomeController {

	
	@Autowired
	private ProductService productService;	
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());


	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		
		// try to get authentication info in case user comes from login page
		String email = null;
		try {
			email = SecurityUtils.getUserName();						

		}catch(Exception e) {
			logger.warn("method index: Wrong with SecurityUtils.getUserName() ");
		}
		
		// use case: user login, redirect to home page, set session currentUser if it is not set yet
		try {
			if(email != null && session.getAttribute("currentUser") == null) {
				User user = userService.findByEmail(email).get();
				session.setAttribute("currentUser", user);
				session.setAttribute("role", user.getRole());
				logger.trace("index, path(/), user: " + user);
			}
		}catch(Exception e) {
			logger.warn("method index: Wrong with session ");
		}
		
		//List<Product> products = productService.findAll();
		Pageable pageable = PageRequest.of(0, ProductList.pageSize);
		List<Product> products = productService.findAll(pageable).getContent();
//		
//		model.addAttribute("products", products);
		model.addAttribute("isHome", true);
//		return "views/product_list";
		
		return findProductsPaginated(1, ProductList.pageSize, session, model);
	}
	
	@GetMapping("/page/{pageNo}/{pageSize}")
	public String findProductsPaginated(@PathVariable(value="pageNo") int pageNo, @PathVariable(value="pageSize") int pageSize, HttpSession session, Model model) {
		
		// try to get authentication info in case user comes from login page
		String email = null;
		try {
			email = SecurityUtils.getUserName();						

		}catch(Exception e) {
			logger.warn("method index: Wrong with SecurityUtils.getUserName() ");
		}
		
		// use case: user login, redirect to home page, set session currentUser if it is not set yet
		try {
			if(email != null && session.getAttribute("currentUser") == null) {
				User user = userService.findByEmail(email).get();
				session.setAttribute("currentUser", user);
				session.setAttribute("role", user.getRole());
				logger.trace("index, path(/), user: " + user);
			}
		}catch(Exception e) {
			logger.warn("method index: Wrong with session ");
		}
		
		Page<Product> productsPage = productService.findPaginated(pageNo, pageSize);
		
		model.addAttribute("products", productsPage.getContent());
		//model.addAttribute("isHome", true);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",productsPage.getTotalPages());
		model.addAttribute("totalItems",productsPage.getTotalElements());
		
		return "views/product_list";
	}

	/**
	 * This method is used to show product list by category.
	 * It is called when user click holiday name (category) from dropdown menu in the header
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@GetMapping("/category/{categoryId}")
	public String browseProductByCategory(@PathVariable int categoryId, Model model) {
		logger.trace("Enter  browseProductByCategory");
		Optional<Category> category = categoryService.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new RuntimeException("no category is found");
		}
		
		Category tempCategory = category.get();
		
		// retrieve product list by category
		List<Product> product = productService.findByCategory(tempCategory);
	
		logger.trace("product: [" + product+"]");
		
		model.addAttribute("products", product);
		model.addAttribute("categoryName", tempCategory.getCategoryName());
		model.addAttribute("categoryId", categoryId);
		
		logger.trace("Exit  browseProductByCategory");
		return "views/product_list";
	}
	
	/**
	 * This method is used to show product list by category and sorted by sort-type chosen by user.
	 * It is called when user choose sort type in category product list page
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@PostMapping("/category")
	public String browseProductByCategory(@RequestParam("sort-type") String sortType, @RequestParam("categoryId") int categoryId, Model model) {
		logger.trace("Enter  browseProductByCategory");
		Optional<Category> category = categoryService.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new RuntimeException("no category is found");
		}
		
		Category tempCategory = category.get();
		
		List<Product> products = null;
	
		// get product list with sort-type and category
		switch(sortType) {
		case "new":
			products = productService.findByCategoryOrderByCreatedOnDesc(tempCategory);
			break;
		case "priceAsc":
			products = productService.findByCategoryOrderByPriceAsc(tempCategory);
			break;
		case "priceDesc":
			products = productService.findByCategoryOrderByPriceDesc(tempCategory);
			break;
		default:
			products = productService.findByCategoryOrderByCreatedOnDesc(tempCategory);
			break;
		}
		
		model.addAttribute("products", products);
		model.addAttribute("categoryName", tempCategory.getCategoryName());
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("sortType", sortType);
		
		logger.trace("sortType: " + sortType);
		logger.trace("Exit  browseProductByCategory");
		return "views/product_list";
	}

	/**
	 * This method is used to show product page for user to configure item
	 * It is called when user click product image from product list page
	 * @param productId
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/product/{productId}")
	public String viewProduct(@PathVariable("productId") int productId, Model model, HttpSession session) {
		
		// try to retrieve login info
		String email = null;
		try {
			email = SecurityUtils.getUserName();						

		}catch(Exception e) {
			//e.printStackTrace();
			logger.warn("viewProduct: Wrong with SecurityUtils.getUserName() ");
		}
		
		// by spring security config, user should already login at this point, retrieve user info and set it in session
		try {
			if(email != null && session.getAttribute("currentUser") == null) {
				User user = userService.findByEmail(email).get();
				session.setAttribute("currentUser", user);
				session.setAttribute("role", user.getRole());
				logger.trace("HomeController, path(/), user: " + user);
			}
		}catch(Exception e) {
			//e.printStackTrace();
			logger.warn("viewProduct: Wrong with session ");
		}
		
		// set up new item with chosen product and pass it to product page
		Optional<Product> tempProduct = productService.findById(productId);

		try {
			if (tempProduct.isEmpty()) {
				throw new ProductNotFoundException("Product with id " + productId + " is not found");
			}
		}catch(ProductNotFoundException e) {
			logger.warn("Exception: " + e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "views/error";
		}
		
		
		Item tempItem = new Item(tempProduct.get());
		model.addAttribute("item", tempItem);

		return "views/product";
	}


	/**
	 * This method is used to show about_us page
	 * @return
	 */
	@GetMapping("/about_us")
	public String showAboutUs() {
		return "views/about_us";
	}

}
