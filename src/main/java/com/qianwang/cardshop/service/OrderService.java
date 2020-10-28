package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.query.Param;

import com.qianwang.cardshop.model.Customer;
import com.qianwang.cardshop.model.Order;

public interface OrderService {
//	public List<Order> findAll();

	public Optional<Order> findById(Integer id);

	public void save(Order order);

//	public void deleteById(Integer id);
	
	public List<Order> getOrderHistory(Customer customer);
	
	List<Order> findByOrderStatus(String orderStatus);
	
	List<Order> findByOrderIds(@Param("ids") Set<Integer> ids);
}
