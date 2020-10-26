package com.qian.cardshop.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * This class is used to store shipping address and contact info of order, 
 * the source could be from customer form input or copied over from customer details info
 * @author qianwang
 *
 */
@Entity
@Table(name = "receivers")
public class Receiver {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="receiver_id")
	private Integer receiverId;
	
	@NotBlank(message="First Name can not blank")
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@NotBlank(message="Last Name can not blank")
	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@NotBlank(message="Address Line 1 can not blank")
	@Column(name="address_line_1", nullable=false)
	private String addressLine1;
	
	@Column(name="address_line_2")
	private String addressLine2;
	
	@NotBlank(message="City can not blank")
	@Column(name="city", nullable=false)
	private String city;
	
	@NotBlank(message="State can not blank")
	@Column(name="state", nullable=false)
	private String state;
	
	@NotBlank(message="Zip code can not blank")
	@Pattern(regexp="^\\d{5}(?:-\\d{4})?$", message="zip code should follow US zip code format")
	@Column(name="zip_code", nullable=false)
	private String zipCode;
	
	@NotBlank(message="Phone can not blank")
	@Pattern(regexp="^[(]?\\d{3}[)]?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message="phone should follow US phone number format")
	@Column(name="phone", nullable=false)
	private String phone;
	
	//@OneToOne - associated
	@OneToOne(mappedBy = "receiver")
	private Order order;
	
	
	// constructors
	
	public Receiver() {}
	
	// copied from customer detail info
	public Receiver(Customer customer) {
		this.firstName = customer.getUser().getFirstName();
		this.lastName = customer.getUser().getLastName();
		this.addressLine1 = customer.getCustomerDetail().getAddressLine1();
		this.addressLine2 = customer.getCustomerDetail().getAddressLine2();
		this.city = customer.getCustomerDetail().getCity();
		this.state = customer.getCustomerDetail().getState();
		this.zipCode = customer.getCustomerDetail().getZipCode();
		this.phone = customer.getCustomerDetail().getPhone();
	}

	public Receiver(String firstName, String lastName, String addressLine1, String addressLine2, String city,
			String state, String zipCode, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phone = phone;
	}

	
	// getters and setters
	
	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	// toString
	
	@Override
	public String toString() {
		return "Receiver [receiverId=" + receiverId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city + ", state="
				+ state + ", zipCode=" + zipCode + ", phone=" + phone + "]";
	}

	// hashCode and equals
	
	@Override
	public int hashCode() {
		return Objects.hash(receiverId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receiver other = (Receiver) obj;
		return Objects.equals(receiverId, other.receiverId);
	}
	
}
