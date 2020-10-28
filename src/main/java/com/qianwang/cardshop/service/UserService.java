package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.User;

public interface UserService {
//	public List<User> findAll();

	public Optional<User> findById(Integer id);

	public void save(User user);

//	public void deleteById(Integer id);

	public Optional<User> findByEmail(String userName);
}
