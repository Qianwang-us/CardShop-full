package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.ProductRepository;
import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.Product;
import com.qianwang.cardshop.service.ProductService;

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

//	@Override
//	public void save(Product product) {
//		productRepository.save(product);
//	}

//	@Override
//	public void deleteById(Integer id) {
//		productRepository.deleteById(id);
//	}

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

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAllByOrderByCreatedOnDesc(pageable);
	}

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRepository.findAllByOrderByCreatedOnDesc(pageable);
	}

}
