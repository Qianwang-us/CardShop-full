package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
