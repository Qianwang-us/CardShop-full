package com.qian.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qian.cardshop.model.Category;
import com.qian.cardshop.model.Product;


public interface ProductService {
	
	
	public Optional<Product> findById(Integer id);
	
	public List<Product> findAll();
			
//	public void save(Product product);
	
//	public void deleteById(Integer id);

	public List<Product> findByCategory(Category category);
	
	public List<Product> findByCategoryOrderByCreatedOnDesc(Category category);
	public List<Product> findByCategoryOrderByPriceAsc(Category category);
	public List<Product> findByCategoryOrderByPriceDesc(Category category);
}
