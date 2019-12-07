package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
@NamedQuery(name="Product.findAllAvailable", query="SELECT p FROM Product p WHERE p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllByCategory", query="SELECT p FROM Product p WHERE p.categoryBean.name LIKE :category AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllByCategoryId", query="SELECT p FROM Product p WHERE p.categoryBean.id LIKE :category AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllByCategoriesId", query="SELECT p FROM Product p WHERE p.categoryBean.id IN :categories AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.OrderById", query="SELECT p FROM Product p WHERE p.userBean IS NOT NULL ORDER BY p.id DESC")
@NamedQuery(name="Product.findAllBySeller", query="SELECT p FROM Product p WHERE p.userBean.email LIKE :email")
@NamedQuery(name="Product.getProductByName", query="SELECT p FROM Product p WHERE p.name LIKE :name AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllByFreeShipment", query="SELECT p FROM Product p WHERE p.shipPrice <= ?1 AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllBetweenSalePrices", query="SELECT p FROM Product p WHERE p.salePrice BETWEEN ?1 AND ?2 AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllBetweenPrices", query="SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2 AND p.userBean IS NOT NULL")
@NamedQuery(name="Product.findAllByStock", query="SELECT p FROM Product p WHERE p.stock > ?1 AND p.userBean IS NOT NULL")
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

	//bi-directional many-to-one association to Orders_has_Product
	@OneToMany(mappedBy="productBean", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Orders_has_Product> ordersHasProducts;

	//bi-directional many-to-one association to Category
	@ManyToOne()
	@JoinColumn(name="category")
	private Category categoryBean;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name="user")
	private User userBean;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="products2")
	private List<User> users;

	//bi-directional many-to-one association to WishList
	@OneToMany(mappedBy="productBean")
	private List<WishList> wishlists;

	//bi-directional many-to-one association to ShopingCart
	@OneToMany(mappedBy="productBean")
	private List<ShopingCart> shopingcarts;

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

	public List<Orders_has_Product> getOrdersHasProducts() {
		return this.ordersHasProducts;
	}

	public void setOrdersHasProducts(List<Orders_has_Product> ordersHasProducts) {
		this.ordersHasProducts = ordersHasProducts;
	}

	public Orders_has_Product addOrdersHasProduct(Orders_has_Product ordersHasProduct) {
		getOrdersHasProducts().add(ordersHasProduct);
		ordersHasProduct.setProductBean(this);

		return ordersHasProduct;
	}

	public Orders_has_Product removeOrdersHasProduct(Orders_has_Product ordersHasProduct) {
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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<WishList> getWishlists() {
		return this.wishlists;
	}

	public void setWishlists(List<WishList> wishlists) {
		this.wishlists = wishlists;
	}

	public WishList addWishlist(WishList wishlist) {
		getWishlists().add(wishlist);
		wishlist.setProductBean(this);

		return wishlist;
	}

	public WishList removeWishlist(WishList wishlist) {
		getWishlists().remove(wishlist);
		wishlist.setProductBean(null);

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
		shopingcart.setProductBean(this);

		return shopingcart;
	}

	public ShopingCart removeShopingcart(ShopingCart shopingcart) {
		getShopingcarts().remove(shopingcart);
		shopingcart.setProductBean(null);

		return shopingcart;
	}

}