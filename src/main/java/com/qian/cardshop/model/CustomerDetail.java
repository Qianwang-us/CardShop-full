package com.qian.cardshop.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_details")
public class CustomerDetail{
		
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_detail_id")
	private Integer customerDetailId;
	
	@Column(name="address_line_1", nullable=false)
	private String addressLine1;
	
	@Column(name="address_line_2")
	private String addressLine2;
	
	@Column(name="city", nullable=false)
	private String city;
	
	@Column(name="state", nullable=false)
	private String state;
	
	@Column(name="zipCode", nullable=false)
	private String zipCode;
	
	@Column(name="phone")
	private String phone;
	
	//@OneToOne - associated
	@OneToOne(mappedBy = "customerDetail")
	private Customer customer;
	
	public CustomerDetail() {}
	
	public CustomerDetail(Receiver receiver) {
		this.addressLine1 = receiver.getAddressLine1();
		this.addressLine2 = receiver.getAddressLine2();
		this.city = receiver.getCity();
		this.state = receiver.getState();
		this.zipCode = receiver.getZipCode();
		this.phone = receiver.getPhone();
	}

//	public CustomerDetail(String addressLine1, String addressLine2, String city, String state, String zipCode,
//			String phone) {
//		
//		this.addressLine1 = addressLine1;
//		this.addressLine2 = addressLine2;
//		this.city = city;
//		this.state = state;
//		this.zipCode = zipCode;
//		this.phone = phone;
//	}
	
	

	public void update(Receiver receiver) {
		this.addressLine1 = receiver.getAddressLine1();
		this.addressLine2 = receiver.getAddressLine2();
		this.city = receiver.getCity();
		this.state = receiver.getState();
		this.zipCode = receiver.getZipCode();
		this.phone = receiver.getPhone();		
	}

	public Integer getCustomerDetailId() {
		return customerDetailId;
	}

	public void setCustomerDetailId(Integer customerDetailId) {
		this.customerDetailId = customerDetailId;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CustomerDetail [customerDetailId=" + customerDetailId + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
				+ ", phone=" + phone + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerDetailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDetail other = (CustomerDetail) obj;
		return Objects.equals(customerDetailId, other.customerDetailId);
	}

	
	
	
}
