package com.qianwang.cardshop.service;

import java.util.Optional;

import com.qianwang.cardshop.model.Cart;

public interface CartService {

	public Optional<Cart> findById(Integer id);

	public void save(Cart cart);

//	public void deleteById(Integer id);
}
