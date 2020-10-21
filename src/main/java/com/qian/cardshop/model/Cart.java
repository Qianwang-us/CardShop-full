package com.qian.cardshop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="carts")
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Integer cartId;
	
	//@OneToOne-associated
	@OneToOne(mappedBy="cart")	
	private Customer customer;
	
	//@OneToMany-associated
	@OneToMany(mappedBy="cart", cascade = CascadeType.ALL)
	private List<Item> cartItems;
	
	public Cart() {
		cartItems = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
				
		cartItems.add(item);	
		item.setCart(this);
	}
	
	public void updateItem(Item item) {
		// becasue use id to match item, find index of the item which contains the same id
		int index = cartItems.indexOf(item);
		cartItems.set(index, item);
		item.setCart(this);
	}
	
	public void removeItem(Item item) {			
		cartItems.remove(item);	
		item.setCart(null);
	}
	
	public void clear() {
		this.cartItems.forEach(item -> item.setCart(null));
		cartItems = new ArrayList<Item>();
		
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<Item> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(cartId, other.cartId);
	}

	

		
	
	
	
	
}
