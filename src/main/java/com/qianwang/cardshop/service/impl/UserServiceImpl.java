package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.UserRepository;
import com.qianwang.cardshop.model.User;
import com.qianwang.cardshop.security.SecurityConfiguration;
import com.qianwang.cardshop.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	UserRepository userRepository;
		

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//	@Override
//	public List<User> findAll() {
//		return userRepository.findAll();
//	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

//	@Override
//	public void save(User user) {
//		userRepository.save(user);
//	}
	
	@Override
	public void save(User user) {
		user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
		userRepository.save(user);
	

	}

//	@Override
//	public void deleteById(Integer id) {
//		userRepository.deleteById(id);
//	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
