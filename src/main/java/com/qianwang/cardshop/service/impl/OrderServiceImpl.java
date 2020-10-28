package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.OrderRepository;
import com.qianwang.cardshop.model.Customer;
import com.qianwang.cardshop.model.Order;
import com.qianwang.cardshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	OrderRepository orderRepository;
	
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

//	@Override
//	public List<Order> findAll() {
//		return orderRepository.findAll();
//	}

	@Override
	public Optional<Order> findById(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

//	@Override
//	public void deleteById(Integer id) {
//		orderRepository.deleteById(id);
//	}

	@Override
	public List<Order> getOrderHistory(Customer customer) {
		return orderRepository.findTop30ByCustomerOrderByCreatedOnDesc(customer);
	}

	@Override
	public List<Order> findByOrderStatus(String orderStatus) {
		return orderRepository.findByOrderStatusOrderByCreatedOnAsc(orderStatus);
	}

	@Override
	public List<Order> findByOrderIds(Set<Integer> ids) {
		
		return orderRepository.findByOrderIds(ids);
	}

}
