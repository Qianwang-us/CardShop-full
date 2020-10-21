package com.qian.cardshop.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.qian.cardshop.model.User;



public class MyUserDetails implements UserDetails{

	/*
	 * Implementing methods from user details 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private Boolean enabled;
	private List<GrantedAuthority> authorities;
	

	
	public MyUserDetails(User user) {
	this.userId = user.getUserId();
	this.firstName = user.getFirstName();
	this.lastName = user.getLastName();
	this.email=user.getEmail();
	this.password=user.getPassword();
	this.role=user.getRole();
	this.enabled=user.getEnabled();
	this.authorities = Arrays.stream(user.getRole().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		System.out.println(authorities);
		return authorities;
	}



	@Override
	public String getPassword() {
		return password;
	}



	@Override
	public String getUsername() {
		return email;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}



	@Override
	public boolean isEnabled() {
		return true;
	}

}