package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qianwang.cardshop.model.Employee;

public interface EmployeeService {
//	public List<Employee> findAll();

	public Optional<Employee> findById(Integer id);

	public void save(Employee employee);

//	public void deleteById(Integer id);
}
