package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NamedQuery(name="User.findAllByType", query="SELECT u FROM User u WHERE u.type LIKE :type")
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

	//	@Column(name="CURRENT_CONNECTIONS")
	//	private BigInteger currentConnections;

	private String name;

	private String password;

	private int phone;

	@Column(name="postal_code")
	private int postalCode;

	private String surnames;

	//	@Column(name="TOTAL_CONNECTIONS")
	//	private BigInteger totalConnections;

	private int type;

	//	private String user;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="userBean")
	private List<Orders> orders;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="userBean")
	private List<Product> products1;

	//bi-directional many-to-many association to Product
	@ManyToMany
	@JoinTable(
			name="wishlists"
			, joinColumns={
					@JoinColumn(name="user")
			}
			, inverseJoinColumns={
					@JoinColumn(name="product")
			}
			)
	private List<Product> products2;

	//bi-directional many-to-one association to WishList
	@OneToMany(mappedBy="userBean", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WishList> wishlists;

	//bi-directional many-to-one association to ShopingCart
	@OneToMany(mappedBy="userBean")
	private List<ShopingCart> shopingcarts;

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

	//	public BigInteger getCurrentConnections() {
	//		return this.currentConnections;
	//	}
	//
	//	public void setCurrentConnections(BigInteger currentConnections) {
	//		this.currentConnections = currentConnections;
	//	}

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

	//	public BigInteger getTotalConnections() {
	//		return this.totalConnections;
	//	}
	//
	//	public void setTotalConnections(BigInteger totalConnections) {
	//		this.totalConnections = totalConnections;
	//	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	//	public String getUser() {
	//		return this.user;
	//	}
	//
	//	public void setUser(String user) {
	//		this.user = user;
	//	}

	public List<Orders> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public Orders addOrder(Orders order) {
		getOrders().add(order);
		order.setUserBean(this);

		return order;
	}

	public Orders removeOrder(Orders order) {
		getOrders().remove(order);
		order.setUserBean(null);

		return order;
	}

	public List<Product> getProducts1() {
		return this.products1;
	}

	public void setProducts1(List<Product> products1) {
		this.products1 = products1;
	}

	public Product addProducts1(Product products1) {
		getProducts1().add(products1);
		products1.setUserBean(this);

		return products1;
	}

	public Product removeProducts1(Product products1) {
		getProducts1().remove(products1);
		products1.setUserBean(null);

		return products1;
	}

	public List<Product> getProducts2() {
		return this.products2;
	}

	public void setProducts2(List<Product> products2) {
		this.products2 = products2;
	}

	public List<WishList> getWishlists() {
		return this.wishlists;
	}

	public void setWishlists(List<WishList> wishlists) {
		this.wishlists = wishlists;
	}

	public WishList addWishlist(WishList wishlist) {
		getWishlists().add(wishlist);
		wishlist.setUserBean(this);

		return wishlist;
	}

	public WishList removeWishlist(WishList wishlist) {
		getWishlists().remove(wishlist);
		wishlist.setUserBean(null);

		return wishlist;
	}

	public List<ShopingCart> getShopingcarts() {
		return this.shopingcarts;
	}

	public void setShopingcarts(List<ShopingCart> shopingcarts) {
		this.shopingcarts = shopingcarts;
	}

	public ShopingCart addShopingcart(ShopingCart shopingcart) {
		getShopingcarts().add(shopingcart);
		shopingcart.setUserBean(this);

		return shopingcart;
	}

	public ShopingCart removeShopingcart(ShopingCart shopingcart) {
		getShopingcarts().remove(shopingcart);
		shopingcart.setUserBean(null);

		return shopingcart;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", address=" + address + ", city=" + city + ", country=" + country
				+ ", creditCard=" + creditCard + ", credit_card_CVV=" + credit_card_CVV + ", creditCardExpiration="
				+ creditCardExpiration + ", name=" + name + ", password=" + password + ", phone=" + phone
				+ ", postalCode=" + postalCode + ", surnames=" + surnames + ", type=" + type + "]";
	}

}