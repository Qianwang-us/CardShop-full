package com.qian.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qian.cardshop.entity.Order;

public interface OrderService {
	public List<Order> findAll();

	public Optional<Order> findById(Integer id);

	public void save(Order order);

	public void deleteById(Integer id);
}
