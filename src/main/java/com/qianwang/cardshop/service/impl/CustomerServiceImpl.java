package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.CustomerRepository;
import com.qianwang.cardshop.model.Customer;
import com.qianwang.cardshop.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository customerRepository;
		

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

//	@Override
//	public List<Customer> findAll() {
//		return customerRepository.findAll();
//	}

	@Override
	public Optional<Customer> findById(Integer id) {
		return customerRepository.findById(id);
	}

	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

//	@Override
//	public void deleteById(Integer id) {
//		customerRepository.deleteById(id);
//	}

}
