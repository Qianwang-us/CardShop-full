package com.qian.cardshop.model;


import java.time.LocalDateTime;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private Integer orderId;
	
	//value ={CREATED,INPROCESS,SHIPPED,COMPLETE,CANCELLED}
	@Column(name="order_status", columnDefinition = "varchar(45) default 'CREATED'", nullable=false)
	private String orderStatus;

	//value = {RECEIVER,CUSTOMER}	
	@Column(name="billing_address_type", nullable=false)
	private String billingAddressType;

	//@ManyToOne-FK
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable=false)
	private Customer customer;
	
	// @OneToOne-FK
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable=false)
	private Receiver receiver;

	// @OneToMany-associated
	@OneToMany(mappedBy="order")
	private List<Item> orderItems;

	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	//@Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdOn;

	//value = 'PAYPAL'
	@Column(name="payment_method", columnDefinition = "varchar(45) default 'PAYPAL'")
	private String paymentMethod;
	
	@Column(name="items_total")
	private Double itemsTotal;
	
	@Column(name="shipping")
	private Double shipping;
	
	@Column(name="tax")
	private Double tax;
	
	@Column(name="order_total")
	private Double orderTotal;

	//@OneToOne - FK
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
	private OrderDetail orderDetail;

	public Order() {}
	
	public Order(Customer customer, Receiver receiver, String billingAddressType, Double itemsTotal, Double shipping, Double tax, Double orderTotal) {
		this.billingAddressType = billingAddressType;				
		this.customer = customer;
		customer.addOrder(this);
		this.setReceiver(receiver);
		this.itemsTotal = itemsTotal;
		this.shipping = shipping;
		this.tax = tax;
		this.orderTotal = orderTotal;
		
		this.orderStatus="CREATED";
		this.paymentMethod="PAYPAL";
	}



	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBillingAddressType() {
		return billingAddressType;
	}

	public void setBillingAddressType(String billingAddressType) {
		this.billingAddressType = billingAddressType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {				
		this.customer = customer;
		//customer.addOrder(this);
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
		receiver.setOrder(this);
	}

	public List<Item> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Item> items) {		
		this.orderItems = items;
		items.forEach(item -> item.setOrder(this));		
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(Double itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderStatus=" + orderStatus + ", billingAddressType="
				+ billingAddressType + ", createdOn=" + createdOn + ", paymentMethod=" + paymentMethod + ", itemsTotal="
				+ itemsTotal + ", shipping=" + shipping + ", tax=" + tax + ", orderTotal=" + orderTotal + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(orderId, other.orderId);
	}
	
	
}

