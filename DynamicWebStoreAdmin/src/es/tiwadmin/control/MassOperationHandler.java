package es.tiwadmin.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.tiwadmin.info.InformationProperties;
import es.tiwadmin.manager.MessageManager;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.MessageCollection;
import es.tiwadmin.model.User;

public class MassOperationHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/tiw-admin";

		String keysParam = request.getParameter("keys");		
		List<String> keys = Arrays.asList(request.getParameter("keys").split(" "));
		
		
		switch(request.getParameter("operation")) {
			case "mass-remove-products":
				ProductManager pm = new ProductManager(InformationProperties.getStrDatabaseName());
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/productList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				
				for(String key : keys)
					pm.deleteProduct(Integer.parseInt(key));
			return "/productList";
			case "mass-remove-users":
				UserManager um = new UserManager(InformationProperties.getStrDatabaseName());
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/userList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				
				for(String key : keys)
					um.deleteUser(key);
				return "/userList";
			case "mass-remove-messages":
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/productList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				HttpSession session = request.getSession();
				
				@SuppressWarnings("unchecked")
				ArrayList<MessageCollection> sessionMsgs = (ArrayList<MessageCollection>) session.getAttribute("messages");
				
				sessionMsgs.removeIf(elem -> keys.contains(elem.getSender()));
				
				session.setAttribute("messages", sessionMsgs);
				
				return "/messageList";
			case "mass-send-message":
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/userList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				
				String messageBody = request.getParameter("messageBody");
				if(messageBody == null) {
					request.setAttribute("receivers", keysParam);
					return "WEB-INF/jsp/messageForm.jsp";
				}

				
				MessageManager mm = new MessageManager();
				
				for(String key : keys)
					mm.writeJMS(user.getEmail(), key, messageBody);
				
				try {
					response.sendRedirect(request.getContextPath() + "/messageList");
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
				return null;
		}
		
		return "WEB-INF/jsp/404.jsp";
	}
	
	
	
}
