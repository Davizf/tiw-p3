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

import model.Message;
import model.User;

public class ChatController {

	static int USER_TYPE_BUYER = 0;

	public static List<Message> getUserMessages(String receiver) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		List<Message> messages = null;

		WebTarget webTarget = client.target("http://localhost:11188");
		WebTarget webTargetPath = webTarget.path("messages").queryParam("receiver", receiver);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTPStatuses.OK) {
			Message[] messagesArray = response.readEntity(Message[].class);
			messages = Arrays.asList(messagesArray);
		}
		client.close();

		return messages;
	}

	public static boolean sendMessage(Message message) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11188");
		WebTarget webTargetPath = webTarget.path("messages");
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(message, MediaType.APPLICATION_JSON));

		client.close();

		return response.getStatus() == HTTPStatuses.CREATED;
	}

	public static void sendMessageToAllBuyers(String message, String sender) {

		List<User>buyers = UserController.getAllUsersByType(USER_TYPE_BUYER);
		for(User buyer: buyers) {
			Message messages = new Message(sender, buyer.getEmail(), message );
			sendMessage(messages);
		}

	}

	public static boolean deleteMessage(String id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11188");
		WebTarget webTargetPath = webTarget.path("messages").path(id);
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		client.close();

		return response.getStatus() == HTTPStatuses.OK;
	}

}