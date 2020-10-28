package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qianwang.cardshop.model.Category;

public interface CategoryService {
//	public List<Category> findAll();

	public Optional<Category> findById(Integer id);

//	public void save(Category category);
//
//	public void deleteById(Integer id);
}
