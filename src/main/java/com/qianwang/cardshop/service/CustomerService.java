package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qianwang.cardshop.model.Customer;

public interface CustomerService {
//	public List<Customer> findAll();

	public Optional<Customer> findById(Integer id);

	public void save(Customer customer);

//	public void deleteById(Integer id);
}
