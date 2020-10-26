package com.qian.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qian.cardshop.model.CustomerDetail;

public interface CustomerDetailService {
//	public List<CustomerDetail> findAll();

	public Optional<CustomerDetail> findById(Integer id);

	public void save(CustomerDetail customerDetail);

//	public void deleteById(Integer id);
}
