package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
