package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import managers.UserManager;
import model.Product;
import model.User;
import model.WishList;

public class UserController {

	public static final int USER_TYPE_SELLER = 1;

	public static User getUserInformation(String email) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		User user = manager.getUser(email);
		factory.close();
		return user;
	}
	
	public static void addUser(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}
	
	public static void modifyUser(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}
	
	public static void deleteUser(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}

	public static ArrayList<Product> getWishListProduct(User user) {
		List<WishList> wishLists = user.getWishlists();
		ArrayList<Product> wishListProducts =  new ArrayList<Product>();

		for(WishList wishList : wishLists) {
			wishListProducts.add(wishList.getProductBean());
		}

		return wishListProducts;
	}

	public static List<User> getAllUsersByType(int type){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		List<User> buyers= manager.getAllUsersByType(type);
		factory.close();
		return buyers;
	}
	
	public static boolean checkUserEmail(String email) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		boolean check =  manager.checkUserEmail(email);
		factory.close();
		return check;
	}
	
	public static boolean verifyUser(String email, String password) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		UserManager manager = new UserManager();
		manager.setEntityManagerFactory(factory);
		boolean check =  manager.verifyUser(email, password);
		factory.close();
		return check;
	}


}