package com.qian.cardshop;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qian.cardshop.dao.CustomerRepository;
import com.qian.cardshop.dao.OrderRepository;
import com.qian.cardshop.dao.ProductRepository;
import com.qian.cardshop.dao.UserRepository;
import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.Order;

@SpringBootTest
public class RepositoriesTests {
	
	//helper repository
	@Autowired
	private CustomerRepository customerRepository;
	
	// test target repository
	@Autowired
	private OrderRepository orderRepositroy;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@Test
	void contextLoads() {
	}
	
	// test methods in order repository
	@Test
	void testFindOrdersTop30ByCustomerOrderByCreatedOnDesc() {
		int customerId = 2;
		Customer customer = customerRepository.findById(customerId).get();
		
		List<Order> orders = orderRepositroy.findTop30ByCustomerOrderByCreatedOnDesc(customer);
		
		assertTrue(orders.size()<=30 && orders.size() > 0);
		Date date0 = orders.get(0).getCreatedOn();
		Date date1 = orders.get(1).getCreatedOn();
		
		assertTrue(date0.compareTo(date1)>=0);
		
	}
	
	@Test
	void testFindOrdersByOrderStatusOrderByCreatedOnAsc() {
		
	}
	
	@Test 
	void testFindOrdersByOrderIds(){
		
	}
	
//	List<Order> findTop30ByCustomerOrderByCreatedOnDesc(Customer customer);
//	List<Order> findByOrderStatusOrderByCreatedOnAsc(String orderStatus);
//
//	@Query("SELECT o FROM Order o WHERE o.orderId IN :ids")
//	List<Order> findByOrderIds(@Param("ids") Set<Integer> ids);
}
