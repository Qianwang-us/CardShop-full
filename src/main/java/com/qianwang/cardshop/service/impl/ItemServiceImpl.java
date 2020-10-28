package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.ItemRepository;
import com.qianwang.cardshop.model.Item;
import com.qianwang.cardshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;
	
	
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

//	@Override
//	public List<Item> findAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Optional<Item> findById(Integer id) {
		return itemRepository.findById(id);
	}

	@Override
	public void save(Item item) {
		itemRepository.save(item);
	}

	@Override
	public void deleteById(Integer id) {
		itemRepository.deleteById(id);
	}

}
