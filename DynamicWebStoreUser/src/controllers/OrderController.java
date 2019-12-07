package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import managers.OrderManager;
import model.Orders;
import model.ProductInCart;

public class OrderController {

	public static boolean checkProductsStock(ArrayList<ProductInCart> productsInCart) {
		for(ProductInCart productInCart : productsInCart) {
			if(productInCart.getQuantity() > productInCart.getProduct().getStock()) {
				return false;
			}	
		}
		
		return true;		
	}
	
	public static List<Orders> getOrdersByUser(String email) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		OrderManager manager = new OrderManager();
		manager.setEntityManagerFactory(factory);
		List<Orders> orders = manager.getOrdersByUser(email);
		factory.close();
		return orders;
	}
	
	public static void createOrder(Orders order) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		OrderManager manager = new OrderManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}
}