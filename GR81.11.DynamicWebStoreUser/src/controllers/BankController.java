package controllers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import model.Transaction;

public class BankController {

	public static String sendTransaction(Transaction transaction) {
		String transaction_id = "";
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget webTarget = client.target("http://localhost:11199");
		WebTarget webTargetPath = webTarget.path("transactions");
		Invocation.Builder invocationBuilder = webTargetPath.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		if (response.getStatus() == HTTPStatuses.OK) {
			transaction_id = response.readEntity(String.class);
		}
		client.close();

		return transaction_id;
	}

}