package com.qian.cardshop.service;

import java.util.Optional;

import com.qian.cardshop.model.Cart;

public interface CartService {

	public Optional<Cart> findById(Integer id);

	public void save(Cart cart);

//	public void deleteById(Integer id);
}
