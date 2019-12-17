package model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the orders_has_products database table.
 * 
 */
@Entity
@Table(name="orders_has_products")
@NamedQuery(name="Orders_has_Product.findAll", query="SELECT o FROM Orders_has_Product o")
public class Orders_has_Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="product_price")
	private BigDecimal productPrice;

	private int quantity;

	@Column(name="ship_price")
	private BigDecimal shipPrice;

	//bi-directional many-to-one association to Order
	@ManyToOne()
	@JoinColumn(name="id_order")
	private Orders order;

	//bi-directional many-to-one association to Product
	@ManyToOne()
	@JoinColumn(name="product")
	private Product productBean;

	public Orders_has_Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getShipPrice() {
		return this.shipPrice;
	}

	public void setShipPrice(BigDecimal shipPrice) {
		this.shipPrice = shipPrice;
	}

	public Orders getOrder() {
		return this.order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Product getProductBean() {
		return this.productBean;
	}

	public void setProductBean(Product productBean) {
		this.productBean = productBean;
	}
	
	public double getCost() { 
		double cost = quantity * productPrice.doubleValue() + shipPrice.doubleValue();

		return  cost=Math.floor(cost * 100) / 100;
	}

}