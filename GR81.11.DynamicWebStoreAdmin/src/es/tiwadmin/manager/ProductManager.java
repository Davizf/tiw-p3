package es.tiwadmin.manager;

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

import es.tiwadmin.model.Order;
import es.tiwadmin.model.OrdersHasProduct;
import es.tiwadmin.model.Product;

public class ProductManager {
	private static final String PATH = "http://localhost:11133/products";

	public static Product getProduct(Integer id) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		Product product = null;
		
		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(id.toString());
		
		Invocation.Builder invocationbuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationbuilder.get();
		
		if(response.getStatus() == 200)
			product = response.readEntity(Product.class);
		
		client.close();
		
//		if(product != null) {
//			List<Order> orders = OrderManager.findAllOrdersByProduct(id);
//			
//			for(Order order : orders)
//				for(OrdersHasProduct ohp : order.getOrdersHasProducts())
//					System.out.println("############### ORDER: " + ohp.getOrder() + " ###############");
//			
//			//System.out.println("############### ");
//			
//			for(Order order : orders) {
//				for(OrdersHasProduct ohp : order.getOrdersHasProducts())
//					product.addOrdersHasProduct(ohp);
//			}
//		}
		
		return product;
	}
	
	public static boolean deleteProduct(Integer productId) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		
		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(productId.toString());
		
		Invocation.Builder invocationbuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationbuilder.delete();
		
		return response.getStatus() == 200;
	}
	
	public static boolean createProduct(Product product) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(product, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == 201;
	}
	
	public static boolean updateProduct(Product product) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(product.getId() + "");

		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(product, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == 200;
	}
	
	public static List<Product> findAllUserProducts(String email) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Product> products = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("seller", email);

		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if(response.getStatus() == 200)
			products = Arrays.asList(response.readEntity(Product[].class));
		
		client.close();
		return products;
	}
	
	public static List<Product> findAllProducts() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		List<Product> products = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("admin", true);

		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			products = Arrays.asList(response.readEntity(Product[].class));
		
		client.close();
		return products;
	}
	
}
