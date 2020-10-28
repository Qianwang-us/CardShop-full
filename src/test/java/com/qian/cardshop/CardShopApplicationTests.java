package com.qian.cardshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qian.cardshop.controller.HomeController;
import com.qian.cardshop.dao.ProductRepository;
import com.qian.cardshop.model.Product;

@SpringBootTest
class CardShopApplicationTests {	

	@Autowired
	private HomeController homeController;
	
	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}
	
	
}
