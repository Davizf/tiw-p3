package controllers;

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
import model.WishList;

public class WishListController {
	private static final String PATH = "http://localhost:11166/wishlist";
	
	public static boolean addWishList(WishList wishList) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		//WebTarget webTargetPath = webTarget.path("users");
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(wishList, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == 201;
	}
	
	public static WishList getWishListByUserAndProduct(String email, Integer productId) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WishList wl = null;
		
		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("user_email", email).queryParam("product_id", productId.toString());
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		if (response.getStatus() == 200)
			wl = response.readEntity(WishList.class);
		
		client.close();
		return wl;
	}
	
	public static List<WishList> getWishListByUser(String email) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<WishList> wls = null;
		
		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("user_email", email);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		if (response.getStatus() == 200)
			wls = Arrays.asList(response.readEntity(WishList[].class));
		
		client.close();
		return wls;
	}
	
	public static boolean deleteWishList(WishList wishList) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(wishList.getId() + "");
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		client.close();
		return response.getStatus() == 200;
	}
	
	public static boolean checkWishListProducts(String email, Product product) {
		return getWishListByUserAndProduct(email, product.getId()) != null;
	}
	
}
