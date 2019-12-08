package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import managers.UserManager;
import model.Product;
import model.User;
import model.WishList;

public class UserController {

	public static final int USER_TYPE_SELLER = 1;

	public static User getUser(String email) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		User user = null;
		
		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").path(email);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		if(response.getStatus() == 200) {
			user = response.readEntity(User.class);	
		}
		client.close();
		
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
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		List<User> users = null;
		
		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").queryParam("type", type);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		if(response.getStatus() == 200) {
			User[] usersArray = response.readEntity(User[].class);	
			users = Arrays.asList(usersArray);
		}
		client.close();
		

		return users;
	}
	
	public static boolean checkUserEmail(String email) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		User user = null;
		
		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").path(email);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		if(response.getStatus() == 200) {
			user = response.readEntity(User.class);	
		}
		client.close();
		
		return user != null;
	}
	
	public static boolean verifyUser(String email, String password) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").path(email);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if(response.getStatus() == 200) {
			User user = response.readEntity(User.class);
			client.close();
			
			return user.getPassword().equals(password);
		} 
		client.close();
	
		return false;
	}


}