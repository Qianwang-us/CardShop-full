package com.qian.cardshop.service;

import java.util.List;
import java.util.Optional;

import com.qian.cardshop.entity.Receiver;

public interface ReceiverService {
	public List<Receiver> findAll();

	public Optional<Receiver> findById(Integer id);

	public void save(Receiver receiver);

	public void deleteById(Integer id);
}
