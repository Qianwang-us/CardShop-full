package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.CartRepository;
import com.qianwang.cardshop.model.Cart;
import com.qianwang.cardshop.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	private CartRepository cartRepository;

	

	public CartServiceImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	@Override
	public Optional<Cart> findById(Integer id) {
		return cartRepository.findById(id);
	}

	@Override
	public void save(Cart cart) {
		cartRepository.save(cart);
	}

//	@Override
//	public void deleteById(Integer id) {
//		cartRepository.deleteById(id);
//	}

}
