package com.qian.cardshop.model;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class is used as admin of the website, 
 * associated class User store login/register info which has role 'ROLE_ADMIN'.
 * 
 * @author qianwang
 *
 */
@Entity
@Table(name = "employees")
public class Employee{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer employeeId;
	
	@OneToOne(mappedBy="employee")
	private User user;
	
	// constructors 
	
	public Employee() {}
	

	// getters and setters

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// toString

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + "]";
	}

	// hashCode and equals
	
	@Override
	public int hashCode() {
		return Objects.hash(employeeId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(employeeId, other.employeeId);
	}
	
}
