package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
