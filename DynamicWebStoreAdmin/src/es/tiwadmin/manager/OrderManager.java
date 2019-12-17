package es.tiwadmin.manager;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import es.tiwadmin.model.Order;

public class OrderManager {
	private static final String PATH = "http://localhost:11155/order";
	
	public static List<Order> findAllOrders() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Order> orders = null;

		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			orders = Arrays.asList(response.readEntity(Order[].class));
		
		client.close();
		return orders;
	}
	
	public static List<Order> findAllOrdersByUser(String email) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Order> orders = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("user_email", email);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			orders = Arrays.asList(response.readEntity(Order[].class));
		
		client.close();
		return orders;
	}
	
	public static List<Order> findAllOrdersByProduct(Integer productId) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Order> orders = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("product_id", productId);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			orders = Arrays.asList(response.readEntity(Order[].class));
		
		client.close();
		return orders;
	}
	
}
