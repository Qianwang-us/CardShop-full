package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
