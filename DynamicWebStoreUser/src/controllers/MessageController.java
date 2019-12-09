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

import model.Messages;
import model.User;

public class MessageController {
	
	public static final int HTTP_STATUS_CREATED = 201;
	public static final int HTTP_STATUS_OK = 200;

	public static List<Messages> getUserMessages(String receiver) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		List<Messages> messages = null;

		WebTarget webTarget = client.target("http://localhost:11188");
		WebTarget webTargetPath = webTarget.path("messages").path(receiver);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200) {
			Messages[] messagesArray = response.readEntity(Messages[].class);
			messages = Arrays.asList(messagesArray);
		}
		client.close();

		return messages;
	}

	public static boolean sendMessage(Messages message) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11188");
		WebTarget webTargetPath = webTarget.path("messages");
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(message, MediaType.APPLICATION_JSON));

		client.close();

		return response.getStatus() == HTTP_STATUS_CREATED;
	}
	
	public static void sendMessageToAllBuyers(String message, String sender) {

		List<User>buyers = UserController.getAllUsersByType(0);
		for(User buyer: buyers) {
			Messages messages = new Messages();
			messages.setSender(sender);
			messages.setReceiver(buyer.getEmail());
			messages.setMessage(message);
			sendMessage(messages);
		}

	}





}