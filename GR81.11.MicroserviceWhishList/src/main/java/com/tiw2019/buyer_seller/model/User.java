package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String address;

	private String city;

	private String country;

	@Column(name="credit_card")
	private String creditCard;

	private int credit_card_CVV;

	@Column(name="credit_card_expiration")
	private String creditCardExpiration;

	private String name;

	private String password;

	private int phone;

	@Column(name="postal_code")
	private int postalCode;

	private String surnames;

	private int type;

//	//bi-directional many-to-one association to Order
//	@OneToMany(mappedBy="userBean")
//	private List<Order> orders;
//
//	//bi-directional many-to-one association to Product
//	@OneToMany(mappedBy="userBean")
//	private List<Product> products;
//
//	//bi-directional many-to-one association to ShopingCart
//	@OneToMany(mappedBy="userBean")
//	private List<ShopingCart> shopingCarts;
//
//	//bi-directional many-to-one association to WishList
//	@OneToMany(mappedBy="userBean")
//	private List<WishList> wishLists;

	public User() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public int getCredit_card_CVV() {
		return this.credit_card_CVV;
	}

	public void setCredit_card_CVV(int credit_card_CVV) {
		this.credit_card_CVV = credit_card_CVV;
	}

	public String getCreditCardExpiration() {
		return this.creditCardExpiration;
	}

	public void setCreditCardExpiration(String creditCardExpiration) {
		this.creditCardExpiration = creditCardExpiration;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhone() {
		return this.phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

//	public List<Order> getOrders() {
//		return this.orders;
//	}
//
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
//
//	public Order addOrder(Order order) {
//		getOrders().add(order);
//		order.setUserBean(this);
//
//		return order;
//	}
//
//	public Order removeOrder(Order order) {
//		getOrders().remove(order);
//		order.setUserBean(null);
//
//		return order;
//	}
//
//	public List<Product> getProducts() {
//		return this.products;
//	}
//
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}
//
//	public Product addProduct(Product product) {
//		getProducts().add(product);
//		product.setUserBean(this);
//
//		return product;
//	}
//
//	public Product removeProduct(Product product) {
//		getProducts().remove(product);
//		product.setUserBean(null);
//
//		return product;
//	}
//
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
//		shopingCart.setUserBean(this);
//
//		return shopingCart;
//	}
//
//	public ShopingCart removeShopingCart(ShopingCart shopingCart) {
//		getShopingCarts().remove(shopingCart);
//		shopingCart.setUserBean(null);
//
//		return shopingCart;
//	}
//
//	public List<WishList> getWishLists() {
//		return this.wishLists;
//	}
//
//	public void setWishLists(List<WishList> wishLists) {
//		this.wishLists = wishLists;
//	}
//
//	public WishList addWishList(WishList wishList) {
//		getWishLists().add(wishList);
//		wishList.setUserBean(this);
//
//		return wishList;
//	}
//
//	public WishList removeWishList(WishList wishList) {
//		getWishLists().remove(wishList);
//		wishList.setUserBean(null);
//
//		return wishList;
//	}

}