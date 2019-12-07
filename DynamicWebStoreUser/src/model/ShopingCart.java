package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the shopingcarts database table.
 * 
 */
@Entity
@Table(name="shopingcarts")
@NamedQuery(name="ShopingCart.findAll", query="SELECT s FROM ShopingCart s")
public class ShopingCart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int quantity;

	//bi-directional many-to-one association to Product
	@ManyToOne()
	@JoinColumn(name="product")
	private Product productBean;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name="user")
	private User userBean;

	public ShopingCart() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProductBean() {
		return this.productBean;
	}

	public void setProductBean(Product productBean) {
		this.productBean = productBean;
	}

	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

}