package com.qianwang.cardshop.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qianwang.cardshop.model.Customer;
import com.qianwang.cardshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findTop30ByCustomerOrderByCreatedOnDesc(Customer customer);
	List<Order> findByOrderStatusOrderByCreatedOnAsc(String orderStatus);

	@Query("SELECT o FROM Order o WHERE o.orderId IN :ids")
	List<Order> findByOrderIds(@Param("ids") Set<Integer> ids);
}
