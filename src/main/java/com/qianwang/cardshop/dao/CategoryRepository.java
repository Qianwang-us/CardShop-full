package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
