package es.tiwadmin.control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.tiwadmin.info.InformationProperties;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.User;

public class UserManipulationHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/tiw-admin";

		UserManager um = new UserManager(InformationProperties.getStrDatabaseName());
		
		if(request.getRequestURI().contains("Remove")) {
			um.deleteUser(request.getParameter("itemPK"));
			return "/userList";
		}
		
		ArrayList<String> errors = new ArrayList<String>();
		
		String password = request.getParameter("password");
		if(!password.equals(request.getParameter("password-check")))
			errors.add("Passwords do not match");
		
		User newUser = um.getUser(request.getParameter("email"));
		
		String action = request.getParameter("action");
		if(action.equals("create") && newUser != null)
			errors.add("Email already exists for another user");

		if(password.isEmpty() && action.equals("create"))	errors.add("Password cannot be empty");
		if(request.getParameter("email").isEmpty()) 		errors.add("Email cannot be empty");
		if(request.getParameter("name").isEmpty()) 			errors.add("Name cannot be empty");
		if(request.getParameter("surnames").isEmpty()) 		errors.add("Surnames cannot be empty");
		if(request.getParameter("phone-number").isEmpty()) 	errors.add("Phone number cannot be empty");
		if(request.getParameter("postal-code").isEmpty()) 	errors.add("Postal code cannot be empty");
		if(request.getParameter("address").isEmpty()) 		errors.add("Address cannot be empty");
		if(request.getParameter("city").isEmpty()) 			errors.add("City cannot be empty");
		if(request.getParameter("country").isEmpty()) 		errors.add("Country cannot be empty");
		if(request.getParameter("role").isEmpty()) 			errors.add("Role must be selected");
		if(request.getParameter("card-number").isEmpty()) 	errors.add("Card Number cannot be empty");
		if(request.getParameter("cvv").isEmpty()) 			errors.add("CVV cannot be empty");
		if(request.getParameter("card-expires").isEmpty()) 	errors.add("Card Expiration cannot be empty");

		try {
			Integer.parseInt(request.getParameter("phone-number"));
		} catch(NumberFormatException nfe) {
			errors.add("Pnone number cannor contain any non-numeric characters");
		}
		
		if(errors.size() != 0) {
			request.setAttribute("errors", errors);
			return "WEB-INF/jsp/wrongParams.jsp";
		}
		
		//If the user desn't exist, create a new one
		newUser = newUser == null ? new User() : newUser;
		
		if(!password.isEmpty())
			newUser.setPassword(password);
		newUser.setEmail(request.getParameter("email"));
		newUser.setName(request.getParameter("name"));
		newUser.setSurnames(request.getParameter("surnames"));
		newUser.setPhone(Integer.parseInt(request.getParameter("phone-number")));
		newUser.setPostalCode(Integer.parseInt(request.getParameter("postal-code")));
		newUser.setAddress(request.getParameter("address"));
		newUser.setCity(request.getParameter("city"));
		newUser.setCountry(request.getParameter("country"));
		newUser.setType(mapRole(request.getParameter("role")));
		
		newUser.setCreditCard(request.getParameter("card-number"));
		newUser.setCredit_card_CVV(Integer.parseInt(request.getParameter("cvv")));
		newUser.setCreditCardExpiration(transformDate(request.getParameter("card-expires")));
		
		
		if(action.equals("edit"))
			um.updateUser(newUser);
		else
			um.createUser(newUser);
		
		request.setAttribute("item", um.getUser(newUser.getEmail()));
		return "WEB-INF/jsp/userDetail.jsp";
	}
	
	private Byte mapRole(String role) {
		switch(role) {
			case "buyer":
				return 0;
			case "seller":
				return 1;
			case "admin":
				return 2;
			default:
				return -1;
		}
	}
	
	private String transformDate(String date) {
		String[] tokens = date.split("-");
		String out = "";
		
		for(int i = tokens.length - 1; i >= 0; i--)
			out += tokens[i] + (i > 0 ? "/" : "");
	
		return out;
	}

}
