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

import es.tiwadmin.model.Category;

public class CategoryManager {
	private static final String PATH = "http://localhost:11133/categories";

	public static Category getCategory(Integer categoryId) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		Category cat = null;
		
		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(categoryId.toString());
		
		Invocation.Builder invocationbuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationbuilder.get();
		
		if(response.getStatus() == 200)
			cat = response.readEntity(Category.class);
		
		client.close();
		return cat;
	}
	
	public static List<Category> findAllCategories() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<Category> cats = null;
		
		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationbuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationbuilder.get();
		
		if (response.getStatus() == 200)
			cats = Arrays.asList(response.readEntity(Category[].class));
			
		client.close();
		return cats;
		
		
	}
}
