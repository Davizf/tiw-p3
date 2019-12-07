package controllers;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import managers.ProductManager;
import model.Product;

public class ProductController {
	
	public static final int MAX_STOCK=2147483647, MIN_STOCK=0, NAME_CHARACTER=100, SHORT_DESC_CHARACTER=300;

	public static Product getProduct(int id){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		Product product = manager.getProduct(id);
		factory.close();
		return product;
	}
	
	public static List<Product> getProductsByCategory(String category){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getProductsByCategory(category);
		factory.close();
		return products;
	}
	
	public static List<Product> getProductsByCategory(int category){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getProductsByCategory(category);
		factory.close();
		return products;
	}

	public static List<Product> getProductsByCategories(List<Integer> idCategories) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getProductsByCategories(idCategories);
		factory.close();
		return products;
	}
	
	public static List<Product> getProductsBySeller(String email){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getProductsBySeller(email);
		factory.close();
		return products;
	}


	public static List<Product> getLastProducts(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getLastProducts();
		factory.close();
		return products;
	}

	public static List<Product> getAllProducts(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List<Product> products = manager.getAllAvailableProducts();
		factory.close();
		return products;
	}

	public static boolean deleteProduct(int id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		Product p = manager.getProduct(id);
		p.setUserBean(null);
		try {
			manager.updateProduct(p);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			factory.close();
		}
		return true;
	}

	public static boolean modifyProduct(Product p) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.updateProduct(p);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			factory.close();
		}
		return true;
	}

	public static int addProduct(Product p) {
		int id=-1;
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createProduct(p);
			id=p.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			factory.close();
		}
		return id;
	}
	
	public static void updateStock(Product product, int quantity) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		int stock = product.getStock() - quantity;
		product.setStock(stock);
		try {
			manager.updateProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}

	public static boolean verifyStock(int stock) {
		return stock<MAX_STOCK && stock>MIN_STOCK;
	}
	
	public static List<Product> getProductByName(String title){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List <Product> product = manager.getProductByName(title);
		return product;
	}
	
	public static List<Product> getProductsBetweenPrices(int min, int max){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List <Product> product = manager.getProductsBetweenPrices(min, max);
		return product;
	}
	
	public static List<Product> getProductsBetweenSalePrices(int min, int max){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List <Product> product = manager.getProductsBetweenSalePrices(min, max);
		return product;
	}
	
	public static List<Product> getProductsFreeShipment(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List <Product> product = manager.getProductsByShipPrice(0);
		return product;
	}
	
	public static List<Product> getProductsByStock(int min){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		ProductManager manager = new ProductManager();
		manager.setEntityManagerFactory(factory);
		List <Product> product = manager.getProductsByStock(min);
		return product;
	}

}