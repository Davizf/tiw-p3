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

import es.tiwadmin.model.MyMessage;

public class MessageManager {

	private static final String PATH = "http://localhost:11188/messages";
	static int USER_TYPE_BUYER = 0;
	public static final int HTTP_STATUS_CREATED = 201;
	public static final int HTTP_STATUS_OK = 200;

	public static List<MyMessage> getUserMessages(String receiver) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		List<MyMessage> messages = null;

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.queryParam("receiver", receiver);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == HTTP_STATUS_OK)
			messages = Arrays.asList(response.readEntity(MyMessage[].class));
		
		client.close();
		return messages;
	}

	public static boolean sendMessage(MyMessage message) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(message, MediaType.APPLICATION_JSON));

		client.close();
		return response.getStatus() == HTTP_STATUS_CREATED;
	}
	
	public static boolean deleteMessage(String id) {
		Client client = ClientBuilder.newClient(new ClientConfig());

		WebTarget webTarget = client.target(PATH);
		WebTarget webTargetPath = webTarget.path(id);
		
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		client.close();
		return response.getStatus() == HTTP_STATUS_OK;
	}

}
