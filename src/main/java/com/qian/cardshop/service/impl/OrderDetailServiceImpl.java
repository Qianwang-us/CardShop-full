package com.qian.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qian.cardshop.dao.OrderDetailRepository;
import com.qian.cardshop.entity.OrderDetail;
import com.qian.cardshop.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	OrderDetailRepository orderDetailRepository;
		

	public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
		this.orderDetailRepository = orderDetailRepository;
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public Optional<OrderDetail> findById(Integer id) {
		return orderDetailRepository.findById(id);
	}

	@Override
	public void save(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	@Override
	public void deleteById(Integer id) {
		orderDetailRepository.deleteById(id);
	}

}
