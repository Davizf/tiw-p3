package controllers;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import managers.WishListManager;
import model.Product;
import model.WishList;

public class WishListController {

	public static WishList getWishListByUserAndProduct(String email, int product) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		WishListManager manager = new WishListManager();
		manager.setEntityManagerFactory(factory);
		WishList wishList = (WishList) manager.getWishListByUserAndProduct(email, product).get(0);
		factory.close();
		return wishList;	
	}
	
	public static boolean deleteWishList(WishList wishList) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		WishListManager manager = new WishListManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.deleteWishList(wishList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			factory.close();
		}
		return true;
	}
	
	public static boolean checkWishListProducts(List<WishList> userWishList, Product product) {
		for(WishList wishlist : userWishList) {
			if (product.getId() == wishlist.getProductBean().getId()) {
				return true;
			}
		}
		return false;
	}
}
