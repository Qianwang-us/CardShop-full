package com.qian.cardshop.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//order-item, cart-item
/**
 * This class is used as items in cart or order which can be checked by associated Cart and Order class
 * It contains custom info from customer such as text-color, text-font and custom-text
 * 
 * @author qianwang
 *
 */
@Entity
@Table(name="items")
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id")
	Integer itemId;
	
	@Column(name="quantity", nullable=false)
	private int quantity;	
	
	//value={'Black', 'Blue', 'Purple'}
	@Column(name="text_color")
	private String textColor;
	//value={Elegant, Cute, Lively}
	@Column(name="text_font")
	private String textFont;
	
	@Column(name="custom_top")
	private String customTop;
	
	@Column(name="custom_body")
	private String customBody;
	
	@Column(name="custom_bottom")
	private String customBottom;
	
	//@ManyToOne - FK - owner
	@ManyToOne
	@JoinColumn(name = "product_id", nullable=false)
	private Product product;	
	
	//@ManyToOne - FK - owner
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	//@ManyToOne - FK - owner
	//after checkout, cartItem changed to orderItem
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	
	// constructors
	public Item() {
		this.quantity = 1;
		this.textColor="Black";
		this.textFont="Elegant";
	}
	
	public Item(Product product) {
		this();
		this.product = product;
		
	}

	// getters and setters

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTextFont() {
		return textFont;
	}

	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}

	public String getCustomTop() {
		return customTop;
	}

	public void setCustomTop(String customTop) {
		this.customTop = customTop;
	}

	public String getCustomBody() {
		return customBody;
	}

	public void setCustomBody(String customBody) {
		this.customBody = customBody;
	}

	public String getCustomBottom() {
		return customBottom;
	}

	public void setCustomBottom(String customBottom) {
		this.customBottom = customBottom;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	// toString
	
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", quantity=" + quantity + ", textColor=" + textColor + ", textFont="
				+ textFont + ", customTop=" + customTop + ", customBody=" + customBody + ", customBottom="
				+ customBottom + "]";
	}

	
	// hashCode and equals by itemId
	
	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(itemId, other.itemId);
	}
	
	
}
