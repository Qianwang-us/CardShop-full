package com.qian.cardshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findTop30ByCustomerOrderByCreatedOnDesc(Customer customer);
}
