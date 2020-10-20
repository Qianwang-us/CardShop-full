package com.qian.cardshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qian.cardshop.controller.HomeController;
import com.qian.cardshop.dao.ProductRepository;
import com.qian.cardshop.entity.Product;

@SpringBootTest
class CardShopApplicationTests {	

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private HomeController homeController;
	
	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}
	
	@Test
	public void testFindProductById() {
		Optional<Product> foundProduct = productRepository.findById(1);
		if(foundProduct.isPresent()) {
			System.out.println(foundProduct.get());
		}else {
			System.out.println("No product found");
		}
			
		//assertThat(foundBook.getAuthor()).isEqualTo("Charles Dickens");
	}
}
