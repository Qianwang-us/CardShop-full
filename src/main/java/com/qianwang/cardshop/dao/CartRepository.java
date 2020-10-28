package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
