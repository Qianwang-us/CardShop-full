package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
