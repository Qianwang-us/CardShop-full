package com.qian.cardshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Integer customerId;		
	
	@OneToOne(mappedBy="customer")
	User user;
	

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_detail_id")
	private CustomerDetail customerDetail;

	//@OneToOne - FK
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
	private Cart cart;

	// field for getFavoriteList and getOrderHistory method, may be removed later
	//@ManyToMany - owner
	@ManyToMany
	@JoinTable(
	  name = "customers_like_products", 
	  joinColumns = @JoinColumn(name = "customer_id"), 
	  inverseJoinColumns = @JoinColumn(name = "product_id"))	
	private List<Product> likedProducts;
	
	//@OneToMany - do not need, use by query - limit the count
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

	public Customer() {
		likedProducts = new ArrayList<Product>();
		orders = new ArrayList<Order>();
		cart = new Cart();
		cart.setCustomer(this);	
	}

	
	
	public void addOrder(Order order) {
		order.setCustomer(this);
		this.orders.add(order);		
	}
	
	public void removeOrder(Order order) {
		this.orders.remove(order);		
	}
	

	public CustomerDetail getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetail customerDetail) {
		this.customerDetail = customerDetail;
		customerDetail.setCustomer(this);
	}

	public Cart getCart() {
//		if (cart == null) {
//			cart = new Cart();
//			cart.setCustomer(this);			
//		}
		
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		cart.setCustomer(this);
	}

	public List<Product> getLikedProducts() {
		return likedProducts;
	}

	public void setLikedProducts(List<Product> likedProducts) {
		this.likedProducts = likedProducts;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	@Override
	public int hashCode() {
		return Objects.hash(customerId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerId, other.customerId);
	}

	


	
	
	
	
}