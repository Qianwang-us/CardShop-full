package com.qian.cardshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qian.cardshop.controller.OrderController;
import com.qian.cardshop.model.Order;
import com.qian.cardshop.service.OrderService;

@SpringBootTest
class OrderTests {	

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderController orderController;
	
	@Test
	void contextLoads() {
		assertThat(orderController).isNotNull();
	}
	
	@Test
	public void testFindById() {
		Integer orderId = 3;
		Optional<Order> foundOrder = orderService.findById(orderId);
//		if(foundOrder.isPresent()) {
//			System.out.println(foundOrder.get());
//		}else {
//			System.out.println("No product found");
//		}
		int actualReceiverId = foundOrder.get().getReceiver().getReceiverId();
			
		assertThat(actualReceiverId).isEqualTo(3);
		//assertThat(foundBook.getAuthor()).isEqualTo("Charles Dickens");
	}
}
