package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
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

		if (response.getStatus() == HTTPStatuses.OK) {
			user = response.readEntity(User.class);
		}
		client.close();

		return user;
	}

	public static boolean addUser(User user) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users");
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		client.close();

		return response.getStatus() == HTTPStatuses.CREATED;
	}

	public static boolean modifyUser(User user) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").path(user.getEmail());
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(user, MediaType.APPLICATION_JSON));

		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

	public static boolean deleteUser(User user) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").path(user.getEmail());
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

	public static ArrayList<Product> getWishListProduct(User user) {
		List<WishList> wishLists = WishListController.getWishListByUser(user.getEmail());
		ArrayList<Product> wishListProducts = new ArrayList<Product>();

		for (WishList wishList : wishLists) {
			wishListProducts.add(wishList.getProductBean());
		}

		return wishListProducts;
	}

	public static List<User> getAllUsersByType(int type) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		List<User> users = null;

		WebTarget webTarget = client.target("http://localhost:11144");
		WebTarget webTargetPath = webTarget.path("users").queryParam("type", type);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) {
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

		if (response.getStatus() == HTTPStatuses.OK) {
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

		if (response.getStatus() == HTTPStatuses.OK) {
			User user = response.readEntity(User.class);
			client.close();

			return user.getPassword().equals(password);
		}
		client.close();

		return false;
	}

}