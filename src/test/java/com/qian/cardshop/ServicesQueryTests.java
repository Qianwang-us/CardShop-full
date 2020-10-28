package com.qian.cardshop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qianwang.cardshop.model.Cart;
import com.qianwang.cardshop.model.Category;
import com.qianwang.cardshop.model.Customer;
import com.qianwang.cardshop.model.CustomerDetail;
import com.qianwang.cardshop.model.Employee;
import com.qianwang.cardshop.model.Item;
import com.qianwang.cardshop.model.Order;
import com.qianwang.cardshop.model.Product;
import com.qianwang.cardshop.model.Receiver;
import com.qianwang.cardshop.model.User;
import com.qianwang.cardshop.service.CartService;
import com.qianwang.cardshop.service.CategoryService;
import com.qianwang.cardshop.service.CustomerDetailService;
import com.qianwang.cardshop.service.CustomerService;
import com.qianwang.cardshop.service.EmployeeService;
import com.qianwang.cardshop.service.ItemService;
import com.qianwang.cardshop.service.OrderService;
import com.qianwang.cardshop.service.ProductService;
import com.qianwang.cardshop.service.ReceiverService;
import com.qianwang.cardshop.service.UserService;

/**
 * This test class test query type service methods
 * create/update/delete type service methods test refer to ServicesUpdateDBTests class
 * 
 * @author qianwang
 *
 */
@SpringBootTest
public class ServicesQueryTests {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerDetailService customerDetailService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReceiverService receiverService;
	
	@Autowired
	private UserService userService;
	
	
	// Cart service test
	@ParameterizedTest
	@CsvSource({"1", "2"})
	public void testFindCartById(Integer id) {
		Cart foundCart = cartService.findById(id).get();
		assertNotNull(foundCart);			
	}
	
	// Category service test
	@ParameterizedTest
	@CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9"})
	public void testFindCategoryById(Integer id) {
		Category foundCategory = categoryService.findById(id).get();
		assertNotNull(foundCategory);			
	}
	
	// customer detail service test
	@ParameterizedTest
	@CsvSource({"1", "2", "3"})
	public void testFindCustomerDetailById(Integer id) {
		CustomerDetail foundCustomerDetail = customerDetailService.findById(id).get();
		assertNotNull(foundCustomerDetail);			
	}
	
	// Customer service test
	@ParameterizedTest
	@CsvSource({"1", "2", "3"})
	public void testFindCustomerById(Integer id) {
		Customer foundCustomer = customerService.findById(id).get();
		assertNotNull(foundCustomer);			
	}
	
	// Employee service test
	@ParameterizedTest
	@CsvSource({"1"})
	public void testFindEmployeeById(Integer id) {
		Employee foundEmployee = employeeService.findById(id).get();
		assertNotNull(foundEmployee);			
	}
	
	// Item service test
	@ParameterizedTest
	@CsvSource({"1"})
	public void testFindItemById(Integer id) {
		Item foundItem = itemService.findById(id).get();
		assertNotNull(foundItem);			
	}
	
	// Order service test
	@ParameterizedTest
	@CsvSource({"1"})
	public void testFindOrderById(Integer id) {
		Order foundOrder = orderService.findById(id).get();
		assertNotNull(foundOrder);			
	}
	
	@ParameterizedTest
	@CsvSource({"2"})
	public void testGetOrderHistory(Integer customerId) {
		Customer customer = customerService.findById(customerId).get();
		List<Order> orders = orderService.getOrderHistory(customer);
		
		assertTrue(orders.size()<=30 && orders.size() > 0);
		Date date0 = orders.get(0).getCreatedOn();
		Date date1 = orders.get(1).getCreatedOn();
		
		assertTrue(date0.compareTo(date1)>=0);			
	}
	
	@ParameterizedTest
	@CsvSource({"CREATED", "INPROCESS"})
	public void testFindOrdersByOrderStatus(String orderStatus) {
		List<Order> orders = orderService.findByOrderStatus(orderStatus);
		
		assertThat(orders.size()).isGreaterThan(0);	
		assertThat(orders.get(0).getOrderStatus()).isEqualTo(orderStatus);	
	}
	
	@ParameterizedTest
	@MethodSource("provideOrderIdsForFindOrdersByIds")
	public void testFindOrdersByIds(Set<Integer> ids) {
		List<Order> orders = orderService.findByOrderIds(ids);
		
		assertThat(orders.size()).isEqualTo(ids.size());	
	}
	
	//helper method to testFindOrdersByIds
	private static Stream<Arguments> provideOrderIdsForFindOrdersByIds() {
		Set<Integer> a = new HashSet<Integer>();  
        a.addAll(Arrays.asList(new Integer[] {3, 4, 7}));  
        
        Set<Integer> b = new HashSet<Integer>();  
        b.addAll(Arrays.asList(new Integer[] {1, 6})); 
        
	    return Stream.of(
	      Arguments.of(a),
	      Arguments.of(b)
	    );
	}
	
	// Product service test
	@ParameterizedTest
	@CsvSource({"1", "2"})
	public void testFindProductById(Integer id) {
		Product foundProduct = productService.findById(id).get();
		assertNotNull(foundProduct);			
	}
	
	@Test
	public void testFindAllProduct() {
		List<Product> products = productService.findAll();
		assertThat(products.size()).isGreaterThan(5);			
	}
	
	@ParameterizedTest
	@CsvSource({"3", "5"})
	public void testFindProductByCategory(Integer categoryId) {
		Category category = categoryService.findById(categoryId).get();
		List<Product> products = productService.findByCategory(category);
		assertThat(products.size()).isGreaterThan(1);	
		products.forEach(product -> assertTrue(product.getCategory().getCategoryId() == categoryId));
	}
	
	@ParameterizedTest
	@CsvSource({"3", "5"})
	public void testFindProductByCategoryOrderByCreatedOnDesc(Integer categoryId) {
		Category category = categoryService.findById(categoryId).get();
		List<Product> products = productService.findByCategory(category);
		assertThat(products.size()).isGreaterThan(1);	
		products.forEach(product -> assertTrue(product.getCategory().getCategoryId() == categoryId));
		Date date0 = products.get(0).getCreatedOn();
		Date date1 = products.get(1).getCreatedOn();
		
		assertTrue(date0.compareTo(date1)>=0);
	}
	
	@ParameterizedTest
	@CsvSource({"3", "5"})
	public void testFindProductByCategoryOrderByPriceAsc(Integer categoryId) {
		Category category = categoryService.findById(categoryId).get();
		List<Product> products = productService.findByCategoryOrderByPriceAsc(category);
		assertThat(products.size()).isGreaterThan(1);	
		products.forEach(product -> assertTrue(product.getCategory().getCategoryId() == categoryId));
		Double price0 = products.get(0).getPrice();
		Double price1 = products.get(1).getPrice();
		
		assertTrue(price0 <= price1);
	}
	
	@ParameterizedTest
	@CsvSource({"3", "5"})
	public void testFindProductByCategoryOrderByPriceDesc(Integer categoryId) {
		Category category = categoryService.findById(categoryId).get();
		List<Product> products = productService.findByCategoryOrderByPriceDesc(category);
		assertThat(products.size()).isGreaterThan(1);	
		products.forEach(product -> assertTrue(product.getCategory().getCategoryId() == categoryId));
		Double price0 = products.get(0).getPrice();
		Double price1 = products.get(1).getPrice();
		
		assertTrue(price0 >= price1);
	}
	
	//Receiver service test
	@ParameterizedTest
	@CsvSource({"1", "2"})
	public void testFindReceiverById(Integer id) {
		Receiver foundReceiver = receiverService.findById(id).get();
		assertNotNull(foundReceiver);			
	}
	
	//User service test
	@ParameterizedTest
	@CsvSource({"5", "6"})
	public void testFindUserById(Integer id) {
		User foundUser = userService.findById(id).get();
		assertNotNull(foundUser);			
	}
	
	@ParameterizedTest
	@CsvSource({"test@test.com", "test2@test.com"})
	public void testFindUserByEmail(String email) {
		User foundUser = userService.findByEmail(email).get();
		assertNotNull(foundUser);			
	}
	
	// test save/delete function in service
	// - Cart save
	// - CustomerDetail save
	// - customer save
	// - Employee save
	
	// - Item service - save, delete 
	// - Order save
	// - Receiver save
	// - User save
	
	
	
}
