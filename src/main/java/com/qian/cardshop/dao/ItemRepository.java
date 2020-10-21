package com.qian.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qian.cardshop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
