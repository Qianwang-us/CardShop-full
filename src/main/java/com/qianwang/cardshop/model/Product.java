package com.qianwang.cardshop.model;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is used as product master data
 * @author qianwang
 *
 */
@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="product_name")
	private String productName;	
	
	@Column(name="provider", columnDefinition = "varchar(255) default 'iCard'")
	private String provider;		
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	//include the full path of the image e.g. /images/example.jpg
	@Column(name="image_path")
	private String imagePath;
	
	@Column(name="price", nullable=false)
	private Double price;
	
//	@CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdOn;
//	@Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	private LocalDateTime createdOn;
	
	@ManyToMany(mappedBy = "likedProducts")
	private List<Customer> likedByCustomers;

	// constructors
	public Product() {
		
	}
	
	public Product(String productName, String provider, String imagePath, Double price) {
		this.productName = productName;
		this.provider = provider;
		this.imagePath = imagePath;
		this.price = price;		
	}

	// getters and setters
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Customer> getLikedByCustomers() {
		return likedByCustomers;
	}

	public void setLikedByCustomers(List<Customer> likedByCustomers) {
		this.likedByCustomers = likedByCustomers;
	}

	
	// toString
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", provider=" + provider
				+ ", imagePath=" + imagePath + ", price=" + price + ", createdOn=" + createdOn + "]";
	}

	// hashCode and equals with productId
	
	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return productId == other.productId;
	}

}
