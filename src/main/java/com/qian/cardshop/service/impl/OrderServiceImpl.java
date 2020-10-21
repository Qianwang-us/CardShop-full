package com.qian.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qian.cardshop.dao.OrderRepository;
import com.qian.cardshop.model.Order;
import com.qian.cardshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	OrderRepository orderRepository;
	
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void deleteById(Integer id) {
		orderRepository.deleteById(id);
	}

}
