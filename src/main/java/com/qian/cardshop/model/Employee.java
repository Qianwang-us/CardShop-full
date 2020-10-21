package com.qian.cardshop.model;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer employeeId;
	
	
	public Employee() {}

	

	public Integer getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}



	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + "]";
	}


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
