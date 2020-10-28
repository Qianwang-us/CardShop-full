package com.qian.cardshop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.qian.cardshop.dao.ItemRepository;
import com.qian.cardshop.model.Cart;
import com.qian.cardshop.model.Customer;
import com.qian.cardshop.model.CustomerDetail;
import com.qian.cardshop.model.Employee;
import com.qian.cardshop.model.Item;
import com.qian.cardshop.model.Product;
import com.qian.cardshop.model.Receiver;
import com.qian.cardshop.model.User;
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
import com.qian.cardshop.util.OrderStatus;
import com.qian.cardshop.util.PaymentSummary;

/**
 * This test class test service methods which may create, update or delete records in database
 * query type service methods test refer to ServiceQueryTests class
 * 
 * simulate real customer shopping process and admin order process
 * Customer:
 * 1. save new user as customer
 * 2. add two items to cart
 * 3. save cart (save item by cascading)
 * 4. update a item in the cart, save cart/item
 * 5. delete a item in the cart, save cart
 * 6. save new receiver
 * 7. save new receiver as customer detail(save)
 * 8. save a new order
 * 
 * Admin:
 * 1. save new user as employee
 * 2. update order status and save order
 * 
 * @author qianwang
 *
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ServicesUpdateDBTests_NotUsed {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerDetailService customerDetailService;
	
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
	
	@Autowired
	private ItemRepository itemRepositry;// need extra methods for testing
	
	// class variables used for testing
	// add static to make it be able to transfer data between tests
	private static User user;
	private static Customer customer;
	private static Cart cart;
	private static Receiver receiver;
	private static CustomerDetail customerDetail;
	private static com.qian.cardshop.model.Order order;
	
	private static Employee employee;

	
	// test following save/delete methods in services
	// - Cart save
	// - CustomerDetail save
	// - customer save
	// - Employee save
	// - Item save/delete 
	// - Order save
	// - Receiver save
	// - User save
	
	// Customer
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testSaveUserAndSaveCustomerAndCart() {
		//need to make sure email is unique for the new user
		Long emailIdentifier = System.currentTimeMillis();
		String email=emailIdentifier+"@test.com";
		//User 
		user = new User(email, "test", "Junit", "Customer", true, "ROLE_USER");
		
		//Customer 
		customer = new Customer();
		user.setCustomer(customer);
		userService.save(user); // CustomerService.save() and CartService.save() are called by cascading
		cart = customer.getCart();
		
		assertTrue(user.getUserId() > 0);
		assertTrue(customer.getCustomerId() > 0);
		assertTrue(cart.getCartId() > 0);
	}
	
	@Test
	@Rollback(false)
	@Order(2)
	public void testSaveItemAndSaveCart() {
		Integer productId = 1;
		Product product0 = productService.findById(productId).get();
		
		productId = 2;
		Product product1 = productService.findById(productId).get();
		
		Item item0 = new Item(product0);
		Item item1 = new Item(product1);
		
		
		// add totally 2 items in the cart
		System.out.println("testSaveItemAndSaveCart, Cart: " + cart);
		
		cart.addItem(item0);
		cart.addItem(item1);
		
		cartService.save(cart); // ItemService.save() is called by cascading
		
		// retrieve the cart from database by cartId and retrieve its item list has size 2
		int actualCartItemsSize = cartService.findById(cart.getCartId()).get().getCartItems().size();
		assertThat(actualCartItemsSize).isEqualTo(2);
	}
	
	@Test
	@Rollback(false)
	@Order(3)
	public void testUpdateCartItem() {
		
		Item item = cart.getCartItems().get(0);
		
		int updatedQuantity = item.getQuantity()+1;
		item.setQuantity(updatedQuantity);
		
		cart.updateItem(item);
		cartService.save(cart); // ItemService.save() is called by cascading
		
		// retrieve the item from database by itemId and test if it is equal to updated quantity in item
		int actualQuantity = itemService.findById(item.getItemId()).get().getQuantity();
		assertThat(actualQuantity).isEqualTo(updatedQuantity);	
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testDeleteCartItem() {
		Item item = cart.getCartItems().get(0);
		int deletedItemId = item.getItemId();
		int originalSize = cart.getCartItems().size();
		cart.removeItem(item);
		itemService.deleteById(deletedItemId);
		cartService.save(cart);
		int updatedSize = cart.getCartItems().size();
		
		assertTrue(originalSize - updatedSize == 1);
		assertFalse(itemRepositry.existsById(deletedItemId));
	}
	
	@Test
	@Rollback(false)
	@Order(5)
	public void testSaveReceiver() {
		receiver = new Receiver("Junit", "Test", "Test Str", "", "Boston", "MA", "02222", "0123456789");
		receiverService.save(receiver);
		assertTrue(receiver.getReceiverId() > 0);
	}
	
	// currently website only support that customer detail comes from receiver info
	@Test
	@Rollback(false)
	@Order(6)
	public void testSaveCustomerDetail() {
		customerDetail = new CustomerDetail(receiver);	
		customer.setCustomerDetail(customerDetail);
		customerDetailService.save(customerDetail);
		assertTrue(customerDetail.getCustomerDetailId() > 0);
	}
	
	@Test
	@Rollback(false)
	@Order(7)
	public void testSaveOrder() {
		String billingAddressType = "RECEIVER";
		ArrayList<Double> payment = PaymentSummary.calculatePayment(cart.getCartItems());
		double itemsTotal = payment.get(0);
		double shipping = payment.get(1);
		double tax = payment.get(2);
		double orderTotal = payment.get(3);
		order = new com.qian.cardshop.model.Order(customer, receiver, billingAddressType, itemsTotal, shipping, tax, orderTotal);
		
		List<Item> items = cart.getCartItems();
		int expectedItemsSize = items.size();
		
		// clear the cart first to avoid shared collection exception
		cart.clear();
		cartService.save(cart);
		orderService.save(order);
		order.setOrderItems(items);
		// update and save item with new order id info
		items.forEach(item -> itemService.save(item));
		
		assertTrue(order.getOrderId() > 0);
		int actualOrderItemsSize = orderService.findById(order.getOrderId()).get().getOrderItems().size();
		
		assertThat(actualOrderItemsSize).isEqualTo(expectedItemsSize);
	}
	
	// Admin
	@Test
	@Order(8)
	public void testSaveUserAndSaveEmployee() {
		//need to make sure email is unique for the new user
		Long emailIdentifier = System.currentTimeMillis();
		String email=emailIdentifier+"@test.com";
		//User 
		user = new User(email, "admin", "Junit", "Admin", true, "ROLE_ADMIN");
		
		//Customer 
		employee = new Employee();
		user.setEmployee(employee);
		userService.save(user); // EmployeeService.save() and CartService.save() are called by cascading
		
		assertTrue(user.getUserId() > 0);
		assertTrue(employee.getEmployeeId() > 0);
	}
	
	@Test
	@Rollback(false)
	@Order(9)
	public void testSaveOrderWithUpdatedStatus() {
		String orderStatus = order.getOrderStatus();
		String expectedStatus = OrderStatus.getNextStatus(orderStatus);
		order.setOrderStatus(expectedStatus);
		
		orderService.save(order);
		String actualOrderStatus = orderService.findById(order.getOrderId()).get().getOrderStatus();
		
		assertThat(actualOrderStatus).isEqualTo(expectedStatus);
	}
}
