package es.tiwadmin.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the orders_has_products database table.
 * 
 */
@Entity
@Table(name="orders_has_products")
@NamedQuery(name="OrdersHasProduct.findAll", query="SELECT o FROM OrdersHasProduct o")
public class OrdersHasProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="product_price")
	private BigDecimal productPrice;

	private int quantity;

	@Column(name="ship_price")
	private BigDecimal shipPrice;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product")
	private Product productBean;

	public OrdersHasProduct() {
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

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProductBean() {
		return this.productBean;
	}

	public void setProductBean(Product productBean) {
		this.productBean = productBean;
	}

}