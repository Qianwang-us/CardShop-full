package com.qian.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qian.cardshop.dao.UserRepository;
import com.qian.cardshop.entity.User;
import com.qian.cardshop.security.SecurityConfiguration;
import com.qian.cardshop.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	UserRepository userRepository;
		

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

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

	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
