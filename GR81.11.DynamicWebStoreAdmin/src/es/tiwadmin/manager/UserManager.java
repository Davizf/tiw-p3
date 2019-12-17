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

import es.tiwadmin.model.User;

public class UserManager {
	private static final String PATH = "http://localhost:11144/users";
	
	public static boolean verifyAdmin(String email, String password) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		User user = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(email);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200) {
			user = response.readEntity(User.class);
			client.close();

			return user.getType() == 2 && user.getPassword().equals(password);
		}
		
		client.close();
		return false;
	}
	
	public static User getUser(String email) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		User user = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(email);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			user = response.readEntity(User.class);
		
		client.close();
		
		if(user != null)
			user.setProducts(ProductManager.findAllUserProducts(user.getEmail()));
		
		return user;
	}
	
//	public static boolean userExists(String email) {
//		Client client = ClientBuilder.newClient(new ClientConfig());
//		User user = null;
//
//		WebTarget webTarget = client.target(PATH);
//		WebTarget webTargetPath = webTarget.path(email);
//		
//		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
//		Response response = invocationBuilder.get();
//
//		if (response.getStatus() == 200)
//			user = response.readEntity(User.class);
//		
//		client.close();
//		return user != null;
//	}
	
	public static boolean createUser(User user) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == 201;
	}
	
	public static boolean updateUser(User user) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(user.getEmail());
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(user, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == 200;
	}
	
	public static boolean deleteUser(String userEmail) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(userEmail);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		client.close();
		return response.getStatus() == 200;
	}
	
	public static List<User> findAllUsers() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<User> users = null;

		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200)
			users = Arrays.asList(response.readEntity(User[].class));
		
		client.close();
		
		for(User user : users)
			user.setProducts(ProductManager.findAllUserProducts(user.getEmail()));
		
		return users;
	}
	
}
