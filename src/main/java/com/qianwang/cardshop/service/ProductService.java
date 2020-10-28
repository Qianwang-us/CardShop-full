package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.Product;


public interface ProductService {
	
	
	public Optional<Product> findById(Integer id);
	
	public List<Product> findAll();
	public List<Product> findAll(Pageable pageable);
			
//	public void save(Product product);
	
//	public void deleteById(Integer id);

	public List<Product> findByCategory(Category category);
	
	public List<Product> findByCategoryOrderByCreatedOnDesc(Category category);
	public List<Product> findByCategoryOrderByPriceAsc(Category category);
	public List<Product> findByCategoryOrderByPriceDesc(Category category);
}
