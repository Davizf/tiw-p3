package controllers;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import model.Category;

public class CategoryController {

	private static final String WEB_SERVICE="http://localhost:11133/categories";

	public static ArrayList<Category> getCategories() {
		Category[] categories = null;
		ArrayList<Category> resul = new ArrayList<Category>();

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) {
			categories = response.readEntity(Category[].class);
			for (int i = 0; i < categories.length; i++)
				resul.add(categories[i]);
		}
		client.close();

		return resul;
	}

	public static Category getCategory(String name) {
		Category category = null;

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.queryParam("name", name);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) category = response.readEntity(Category[].class)[0];
		client.close();
		return category;
	}

	public static Category getCategory(int id) {
		Category category = null;

		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target(WEB_SERVICE);

		WebTarget webTargetPath = webTarget.path(""+id);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) category = response.readEntity(Category.class);
		client.close();
		return category;
	}

}