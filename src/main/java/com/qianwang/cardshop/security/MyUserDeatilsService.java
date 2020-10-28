package com.qianwang.cardshop.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.UserRepository;
import com.qianwang.cardshop.model.User;


@Service
public class MyUserDeatilsService implements UserDetailsService {

	// created a query to find user by email
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// find if user exist
		Optional<User> user = userRepo.findByEmail(email);
		// throw an error if not
		user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + email));
		// get me the user
		
		return user.map(MyUserDetails::new).get();
	}

}
