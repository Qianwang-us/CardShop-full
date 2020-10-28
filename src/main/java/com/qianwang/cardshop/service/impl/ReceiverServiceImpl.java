package com.qianwang.cardshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qianwang.cardshop.dao.ReceiverRepository;
import com.qianwang.cardshop.model.Receiver;
import com.qianwang.cardshop.service.ReceiverService;

@Service
public class ReceiverServiceImpl implements ReceiverService{
	ReceiverRepository receiverRepository;

	
	public ReceiverServiceImpl(ReceiverRepository receiverRepository) {
		this.receiverRepository = receiverRepository;
	}

//	@Override
//	public List<Receiver> findAll() {
//		return receiverRepository.findAll();
//	}

	@Override
	public Optional<Receiver> findById(Integer id) {
		return receiverRepository.findById(id);
	}

	@Override
	public void save(Receiver receiver) {
		receiverRepository.save(receiver);
	}

//	@Override
//	public void deleteById(Integer id) {
//		receiverRepository.deleteById(id);
//	}
	
}
