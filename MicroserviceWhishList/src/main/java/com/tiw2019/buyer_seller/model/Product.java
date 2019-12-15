package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Cacheable(false)
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	//bi-directional many-to-one association to OrdersHasProduct
	@OneToMany(mappedBy="productBean")
	@JsonIgnore
	private List<OrdersHasProduct> ordersHasProducts;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category")
	private Category categoryBean;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user")
	private User userBean;

//	//bi-directional many-to-one association to ShopingCart
//	@OneToMany(mappedBy="productBean")
//	private List<ShopingCart> shopingCarts;

	//bi-directional many-to-one association to WishList
	@OneToMany(mappedBy="productBean")
	@JsonIgnore
	private List<WishList> wishLists;

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

	public List<OrdersHasProduct> getOrdersHasProducts() {
		return this.ordersHasProducts;
	}

	public void setOrdersHasProducts(List<OrdersHasProduct> ordersHasProducts) {
		this.ordersHasProducts = ordersHasProducts;
	}

	public OrdersHasProduct addOrdersHasProduct(OrdersHasProduct ordersHasProduct) {
		getOrdersHasProducts().add(ordersHasProduct);
		ordersHasProduct.setProductBean(this);

		return ordersHasProduct;
	}

	public OrdersHasProduct removeOrdersHasProduct(OrdersHasProduct ordersHasProduct) {
		getOrdersHasProducts().remove(ordersHasProduct);
		ordersHasProduct.setProductBean(null);

		return ordersHasProduct;
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

//	public List<ShopingCart> getShopingCarts() {
//		return this.shopingCarts;
//	}
//
//	public void setShopingCarts(List<ShopingCart> shopingCarts) {
//		this.shopingCarts = shopingCarts;
//	}
//
//	public ShopingCart addShopingCart(ShopingCart shopingCart) {
//		getShopingCarts().add(shopingCart);
//		shopingCart.setProductBean(this);
//
//		return shopingCart;
//	}
//
//	public ShopingCart removeShopingCart(ShopingCart shopingCart) {
//		getShopingCarts().remove(shopingCart);
//		shopingCart.setProductBean(null);
//
//		return shopingCart;
//	}

	public List<WishList> getWishLists() {
		return this.wishLists;
	}

	public void setWishLists(List<WishList> wishLists) {
		this.wishLists = wishLists;
	}

	public WishList addWishList(WishList wishList) {
		getWishLists().add(wishList);
		wishList.setProductBean(this);

		return wishList;
	}

	public WishList removeWishList(WishList wishList) {
		getWishLists().remove(wishList);
		wishList.setProductBean(null);

		return wishList;
	}

}