package com.qianwang.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qianwang.cardshop.model.Item;

public interface ItemService {
//	public List<Item> findAll();

	public Optional<Item> findById(Integer id);

	public void save(Item item);

	public void deleteById(Integer id);
}
