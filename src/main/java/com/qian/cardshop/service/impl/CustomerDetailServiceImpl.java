package com.qian.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qian.cardshop.dao.CustomerDetailRepository;
import com.qian.cardshop.entity.CustomerDetail;
import com.qian.cardshop.service.CustomerDetailService;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
	CustomerDetailRepository customerDetailRepository;
		
	
	public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository) {
		this.customerDetailRepository = customerDetailRepository;
	}

	@Override
	public List<CustomerDetail> findAll() {
		return customerDetailRepository.findAll();
	}

	@Override
	public Optional<CustomerDetail> findById(Integer id) {
		return customerDetailRepository.findById(id);
	}

	@Override
	public void save(CustomerDetail customerDetail) {
		customerDetailRepository.save(customerDetail);
	}

	@Override
	public void deleteById(Integer id) {
		customerDetailRepository.deleteById(id);
	}

	

}
