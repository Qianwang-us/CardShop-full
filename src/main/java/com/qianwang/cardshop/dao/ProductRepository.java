package com.qianwang.cardshop.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
//	@Query("SELECT p FROM Product p ORDER BY p.createdOn DESC")
//    public List<Product> findSortByDate();
	
	public List<Product> findAllByOrderByCreatedOnDesc();
	
	//@Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
	public List<Product> findByCategory(Category category);
	public List<Product> findByCategoryOrderByCreatedOnDesc(Category category);
	public List<Product> findByCategoryOrderByPriceAsc(Category category);
	public List<Product> findByCategoryOrderByPriceDesc(Category category);
	
	// added for page use
	public Page<Product> findAllByOrderByCreatedOnDesc(Pageable pageable);
	
	//added for pagination function
	public Page<Product> findByCategory(Category category, Pageable pageable);
	public Page<Product> findByCategoryOrderByCreatedOnDesc(Category category, Pageable pageable);
	public Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageable);
	public Page<Product> findByCategoryOrderByPriceDesc(Category category, Pageable pageable);
	
}
