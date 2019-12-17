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

import model.Orders;
import model.ProductInCart;

public class OrderController {
	private static final String PATH = "http://localhost:11155/order";
	public static final int USER_TYPE_SELLER = 1;
	public static final int HTTP_STATUS_CREATED = 201;
	public static final int HTTP_STATUS_OK = 200;
	
	public static boolean checkProductsStock(ArrayList<ProductInCart> productsInCart) {
		for(ProductInCart productInCart : productsInCart) {
			if(productInCart.getQuantity() > productInCart.getProduct().getStock()) {
				return false;
			}	
		}
		
		return true;
	}
	
	public static List<Orders> getOrdersByUser(String email) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Orders> products = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("user_email", email);

		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if(response.getStatus() == HTTP_STATUS_OK)
			products = Arrays.asList(response.readEntity(Orders[].class));
		
		client.close();
		return products;
	}
	
	public static boolean createOrder(Orders order) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		
		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(order, MediaType.APPLICATION_JSON));
		
		client.close();
		return response.getStatus() == HTTP_STATUS_CREATED;
	}
}