package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
