package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.EmployeeRepository;
import com.qianwang.cardshop.model.Employee;
import com.qianwang.cardshop.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	EmployeeRepository employeeRepository;
	
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

//	@Override
//	public List<Employee> findAll() {
//		// TODO Auto-generated method stub
//		return employeeRepository.findAll();
//	}

	@Override
	public Optional<Employee> findById(Integer id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id);
	}

	@Override
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

//	@Override
//	public void deleteById(Integer id) {
//		employeeRepository.deleteById(id);
//		
//	}
	 
}
