package com.qian.cardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qian.cardshop.model.Product;
import com.qian.cardshop.service.ProductService;
import com.qian.cardshop.util.ProductList;

/**
 * This class is used to send back a list of products with lowest price (size 12)
 * @author qianwang
 *
 */
@RestController
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products/lowest_price")
	public List<Product> getProductsWithLowestPrices(){
		Pageable pageable = PageRequest.of(0, ProductList.pageSize);
		List<Product> products = productService.findAll(pageable);
		return products;
	}

}
