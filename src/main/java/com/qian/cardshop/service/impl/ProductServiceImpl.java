package com.qian.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qian.cardshop.dao.ProductRepository;
import com.qian.cardshop.model.Category;
import com.qian.cardshop.model.Product;
import com.qian.cardshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Optional<Product> findById(Integer id) {
	 
			 return productRepository.findById(id);
 		  
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAllByOrderByCreatedOnDesc();
		//return productRepository.findAll();
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findByCategoryOrderByCreatedOnDesc(Category category) {
		return productRepository.findByCategoryOrderByCreatedOnDesc(category);
	}

	@Override
	public List<Product> findByCategoryOrderByPriceAsc(Category category) {
		return productRepository.findByCategoryOrderByPriceAsc(category);
	}

	@Override
	public List<Product> findByCategoryOrderByPriceDesc(Category category) {
		return productRepository.findByCategoryOrderByPriceDesc(category);
	}

}
