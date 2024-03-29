package controllers;

import java.util.ArrayList;
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

public class ProductController {

	private static final String WEB_SERVICE="http://localhost:11133/products";

	public static final int MAX_STOCK=2147483647, MIN_STOCK=0, NAME_CHARACTER=100, SHORT_DESC_CHARACTER=300, DEFAULT_LAST_PRODUCTS=4;

	// Auxiliar GET functions
	/**
	 * GET {@link List<Product>} from WS with a parameter, empty list if error
	 * @param parameter
	 * @param value of the parameter
	 */
	private static List<Product> getWithParameter(String parameter, String value) {
		List<Product> products = new ArrayList<Product>();

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath;
		if (parameter==null) webTargetPath = webTarget;
		else webTargetPath = webTarget.queryParam(parameter, value);

		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) {
			Product[] productsArr = response.readEntity(Product[].class);
			products=new ArrayList<Product>(productsArr.length);
			for (int i = 0; i < productsArr.length; i++) products.add(productsArr[i]);
		}
		client.close();

		return products;
	}
	/**
	 * GET {@link List<Product>} from WS with 2 parameters (both required), empty list if error
	 * @param parameter
	 * @param value of the parameter
	 */
	private static List<Product> getWithParameters(String parameter1, String value1, String parameter2, String value2) {
		List<Product> products = new ArrayList<Product>();

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.queryParam(parameter1, value1).queryParam(parameter2, value2);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) {
			Product[] productsArr = response.readEntity(Product[].class);
			products=new ArrayList<Product>(productsArr.length);
			for (int i = 0; i < productsArr.length; i++) products.add(productsArr[i]);
		}
		client.close();

		return products;
	}

	public static Product getProduct(int id){
		Product product = null;

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.path(""+id);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) product = response.readEntity(Product.class);
		client.close();

		return product;
	}

	public static List<Product> getProductsByCategory(String category){
		return getWithParameter("category", category);
	}

	public static List<Product> getProductsByCategory(int category){
		return getWithParameter("category_id", ""+category);
	}

	public static List<Product> getProductsByCategories(List<Integer> idCategories) {
		String categoriesStr="";
		for (Integer n : idCategories) categoriesStr+=n+",";
		return getWithParameter("categories", categoriesStr.substring(0, categoriesStr.length()-1));
	}

	public static List<Product> getProductsBySeller(String email){
		return getWithParameter("seller", email);
	}

	public static List<Product> getLastProducts(){
		return getWithParameter("last", ""+DEFAULT_LAST_PRODUCTS);
	}

	public static List<Product> getAllProducts(){
		return getWithParameter(null, null);
	}

	public static boolean deleteProduct(int id) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.path(""+id);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

	public static boolean modifyProduct(Product p) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.path(""+p.getId());
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(p, MediaType.APPLICATION_JSON));
		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

	public static int addProduct(Product p) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(p, MediaType.APPLICATION_JSON));
		int newId = response.readEntity(Integer.class);
		client.close();

		if (response.getStatus() == HTTPStatuses.CREATED)
			return newId;
		else
			return -1;
	}

	public static boolean updateStock(Product product, int quantity) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.path(""+product.getId()).queryParam("stock", quantity);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(product, MediaType.APPLICATION_JSON));
		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

	public static boolean verifyStock(int stock) {
		return stock<MAX_STOCK && stock>MIN_STOCK;
	}

	public static List<Product> getProductByName(String name){
		return getWithParameter("product_name", name);
	}

	public static List<Product> getProductsBetweenPrices(int min, int max){
		return getWithParameters("min", ""+min, "max", ""+max);
	}

	public static List<Product> getProductsBetweenSalePrices(int min, int max){
		return getWithParameters("sale_min", ""+min, "sale_max", ""+max);
	}

	public static List<Product> getProductsFreeShipment(){
		return getWithParameter("ship_price", "0");
	}

	public static List<Product> getProductsByStock(int min){
		return getWithParameter("stock", ""+min);
	}

}