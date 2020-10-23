package com.qian.cardshop.model;


import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class is used to store order processing info including processed person and processed date
 * @author qianwang
 *
 */
@Entity
@Table(name = "order_details")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_detail_id")
	private Integer orderDetailId;
	
	
	// used for admin functions
	//@ManyToOne - FK
	@ManyToOne
	@JoinColumn(name = "processed_by", referencedColumnName="employee_id")
	private Employee processedBy;
	
	@Column(name="processed_on")
	private LocalDate processedOn;
	
	//@ManyToOne - FK
	@ManyToOne
	@JoinColumn(name = "shipped_by", referencedColumnName="employee_id")
	private Employee shippedBy;
	
	@Column(name="shipped_on")
	private LocalDate shippedOn;	
	
	//@ManyToOne - FK
	@ManyToOne
	@JoinColumn(name = "cancelled_by")
	private User cancelledBy;	
	
	@Column(name="cancelled_on")
	private LocalDate cancelledOn;
	
	//@OneToOne - associated
	@OneToOne(mappedBy="orderDetail")	
	private Order order;
	
	
	// constructors
	
	public OrderDetail() {}

	
	// getters and setters
	
	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Employee getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(Employee processedBy) {
		this.processedBy = processedBy;
	}

	public LocalDate getProcessedOn() {
		return processedOn;
	}

	public void setProcessedOn(LocalDate processedOn) {
		this.processedOn = processedOn;
	}

	public Employee getShippedBy() {
		return shippedBy;
	}

	public void setShippedBy(Employee shippedBy) {
		this.shippedBy = shippedBy;
	}

	public LocalDate getShippedOn() {
		return shippedOn;
	}

	public void setShippedOn(LocalDate shippedOn) {
		this.shippedOn = shippedOn;
	}

	public User getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(User cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public LocalDate getCancelledOn() {
		return cancelledOn;
	}

	public void setCancelledOn(LocalDate cancelledOn) {
		this.cancelledOn = cancelledOn;
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
		return "OrderDetail [orderDetailId=" + orderDetailId + ", processedBy=" + processedBy.getEmployeeId() + ", processedOn="
				+ processedOn + ", shippedBy=" + shippedBy.getEmployeeId() + ", shippedOn=" + shippedOn + ", cancelledBy=" + cancelledBy.getUserId()
				+ ", cancelledOn=" + cancelledOn + "]";
	}

	
	// hashCode and equals with orderDetailId
	
	@Override
	public int hashCode() {
		return Objects.hash(orderDetailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetail other = (OrderDetail) obj;
		return Objects.equals(orderDetailId, other.orderDetailId);
	}
	
}


