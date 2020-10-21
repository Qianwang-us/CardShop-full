package com.qian.cardshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.model.Category;
import com.qian.cardshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// that's it ... no need to write any code LOL!
	
	
	
//	@Query("SELECT p FROM Product p ORDER BY p.createdOn DESC")
//    public List<Product> findSortByDate();
	
	public List<Product> findAllByOrderByCreatedOnDesc();
	
	//@Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
	public List<Product> findByCategory(Category category);
	
	public List<Product> findByCategoryOrderByCreatedOnDesc(Category category);
	public List<Product> findByCategoryOrderByPriceAsc(Category category);
	public List<Product> findByCategoryOrderByPriceDesc(Category category);
	
}
