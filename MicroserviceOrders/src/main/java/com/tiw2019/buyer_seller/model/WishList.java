package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the WishLists database table.
 * 
 */
@Entity
@Table(name="WishLists")
@NamedQuery(name="WishList.findAll", query="SELECT w FROM WishList w")
public class WishList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product")
	private Product productBean;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user")
	private User userBean;

	public WishList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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