package com.qian.cardshop.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class is used as product's category. Category id and name are predefined in database.
 * Reference list:
 * ID	Holiday Name
 * 1	Holidays
 * 2	Events
 * 3	Birthday
 * 4	Graduation
 * 5	Chrismas Day
 * 6	Mother's Day
 * 7	Father's Day
 * 8	Valentine's Day
 * 9	New Year's Day

 * 
 * @author qianwang
 *
 */
@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private Integer categoryId;
	
	@Column(name="category_name", nullable=false)
	private String categoryName;
	
	// level = 1 or 2, level 1 is parent level, 2 is sublevel
	@Column(name="level")
	private int level;
	
	//@ManyToOne - FK
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_category_id")
	private Category parent;
	
	//@OneToMany, only exist when level=2
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	
	//constructors
	
	public Category() {}

	public Category(Integer categoryId, String categoryName, int level, Category parent) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.level = level;
		this.parent = parent;
	}

	// getters and setters
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	// toString
	
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", level=" + level + "]";
	}

	// hashCode and equals
	
	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categoryId, other.categoryId);
	}
	
	
	
}
