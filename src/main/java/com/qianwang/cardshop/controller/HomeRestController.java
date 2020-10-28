package com.qianwang.cardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianwang.cardshop.model.Product;
import com.qianwang.cardshop.service.ProductService;
import com.qianwang.cardshop.util.ChristmasDayLeft;
import com.qianwang.cardshop.util.ProductList;

/**
 * This class is used to provide general web service from home page
 * @author qianwang
 *
 */
@RestController
@RequestMapping("/api")
public class HomeRestController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * This method is used to return a list of products with page size. 
	 * Currently no consumer for this method yet.
	 * @author qianwang
	 *
	 */
	@GetMapping("/products")
	public List<Product> getProducts(){
		Pageable pageable = PageRequest.of(0, ProductList.pageSize);
		List<Product> products = productService.findAll(pageable);
		return products;
	}
	
	/**
	 * This method is used to provide days left for next Christmas day
	 * @return
	 */
	@GetMapping("/days_to_christmas")
	public String getDaysToNextChritmasDay(){
		String message;
		
		message = "There are " + ChristmasDayLeft.now() + " days left!";
		
		return message;
	}

}
