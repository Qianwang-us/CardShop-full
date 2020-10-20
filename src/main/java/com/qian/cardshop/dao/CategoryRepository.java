package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
