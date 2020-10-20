package com.qian.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qian.cardshop.entity.Category;
import com.qian.cardshop.entity.User;

public interface UserService {
	public List<User> findAll();

	public Optional<User> findById(Integer id);

	public void save(User user);

	public void deleteById(Integer id);

	public Optional<User> findByEmail(String userName);
}
