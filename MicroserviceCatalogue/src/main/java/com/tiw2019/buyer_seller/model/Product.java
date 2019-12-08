package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	@Column(name="image_path")
	private String imagePath;

	private String name;

	private BigDecimal price;

	@Column(name="sale_price")
	private BigDecimal salePrice;

	@Column(name="ship_price")
	private BigDecimal shipPrice;

	@Column(name="short_description")
	private String shortDescription;

	private int stock;

	//bi-directional many-to-one association to Category
	@ManyToOne()
	@JoinColumn(name="category")
	private Category categoryBean;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name="user")
	private User userBean;

	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getShipPrice() {
		return this.shipPrice;
	}

	public void setShipPrice(BigDecimal shipPrice) {
		this.shipPrice = shipPrice;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Category getCategoryBean() {
		return this.categoryBean;
	}

	public void setCategoryBean(Category categoryBean) {
		this.categoryBean = categoryBean;
	}

	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", description=" + description + ", imagePath=" + imagePath + ", name=" + name
				+ ", price=" + price + ", salePrice=" + salePrice + ", shipPrice=" + shipPrice + ", shortDescription="
				+ shortDescription + ", stock=" + stock + ", categoryBean=" + categoryBean + ", userBean=" + userBean
				+ "]";
	}

}