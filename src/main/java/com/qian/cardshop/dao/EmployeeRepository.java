package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
