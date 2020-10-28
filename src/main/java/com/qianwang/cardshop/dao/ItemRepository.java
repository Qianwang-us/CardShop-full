package com.qianwang.cardshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
