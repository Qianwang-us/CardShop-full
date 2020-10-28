package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.CategoryRepository;
import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	CategoryRepository categoryRepository;
		
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

//	@Override
//	public List<Category> findAll() {
//		return categoryRepository.findAll();
//	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

//	@Override
//	public void save(Category category) {
//		categoryRepository.save(category);
//	}
//
//	@Override
//	public void deleteById(Integer id) {
//		categoryRepository.deleteById(id);		
//	}

}
