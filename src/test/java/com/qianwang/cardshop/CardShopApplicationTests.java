package com.qianwang.cardshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qianwang.cardshop.controller.HomeController;
import com.qianwang.cardshop.dao.ProductRepository;
import com.qianwang.cardshop.model.Product;

@SpringBootTest
class CardShopApplicationTests {	

	@Autowired
	private HomeController homeController;
	
	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}
	
	
}
