package com.qian.cardshop;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qian.cardshop.model.Cart;
import com.qian.cardshop.model.Category;
import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.CustomerDetail;
import com.qian.cardshop.model.Employee;
import com.qian.cardshop.service.CartService;
import com.qian.cardshop.service.CategoryService;
import com.qian.cardshop.service.CustomerDetailService;
import com.qian.cardshop.service.CustomerService;
import com.qian.cardshop.service.EmployeeService;
import com.qian.cardshop.service.ItemService;
import com.qian.cardshop.service.OrderService;
import com.qian.cardshop.service.ProductService;
import com.qian.cardshop.service.ReceiverService;
import com.qian.cardshop.service.UserService;

@SpringBootTest
public class ServicesUpdateDBTests {

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
	
	
	// cart service test
	@ParameterizedTest
	@CsvSource({"1", "2"})
	public void testFindCartById(Integer id) {
		Cart foundCart = cartService.findById(id).get();
		assertNotNull(foundCart);			
	}
	
	// category service test
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
	
	// customer detail service test
	@ParameterizedTest
	@CsvSource({"1", "2", "3"})
	public void testFindCustomerById(Integer id) {
		Customer foundCustomer = customerService.findById(id).get();
		assertNotNull(foundCustomer);			
	}
	
	// employee detail service test
	@ParameterizedTest
	@CsvSource({"1"})
	public void testFindEmployeeById(Integer id) {
		Employee foundEmployee = employeeService.findById(id).get();
		assertNotNull(foundEmployee);			
	}
	
	// test save function in service
	// - Cart save
	// - CustomerDetail save
	// - customer save
	// - employee save
	
	// - item service - find, save, delete (test with above)
	
	
	
	
}
