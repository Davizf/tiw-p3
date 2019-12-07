package model;

import java.io.Serializable;

public class ProductInCart implements Serializable {
	private static final long serialVersionUID = 1L;

	private Product product;
	private int quantity;

	public ProductInCart() {
	}
	public ProductInCart(Product p) {
		product=p;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getCost() {
		return quantity * product.getSalePrice().doubleValue();
	}
	@Override
	public String toString() {
		return "ProductInCart [product=" + product.getName() + ", quantity=" + quantity + ", cost="+ getCost() +"]";
	}

}