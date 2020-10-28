package com.qianwang.cardshop.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianwang.cardshop.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

}
