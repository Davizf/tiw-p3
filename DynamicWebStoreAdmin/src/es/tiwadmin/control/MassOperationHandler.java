package es.tiwadmin.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.tiwadmin.manager.MessageManager;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.MyMessage;
import es.tiwadmin.model.User;

public class MassOperationHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/DynamicWebStoreAdmin";

		String keysParam = request.getParameter("keys");		
		List<String> keys = Arrays.asList(request.getParameter("keys").split(" "));
		
		System.out.println("############### MASSOPHANDLER SAYS KEY[0]: " + keys.get(0) + " ###############");
		
		
		switch(request.getParameter("operation")) {
			case "mass-remove-products":
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/productList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				
				for(String key : keys)
					ProductManager.deleteProduct(Integer.parseInt(key));
			return "/productList";
			case "mass-remove-users":
				if(keysParam.isEmpty()) {
					try {
						response.sendRedirect(request.getContextPath() + "/userList");
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					return null;
				}
				
				for(String key : keys)
					UserManager.deleteUser(key);
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
				
				for(String sender : keys) {
					List<MyMessage> userMsgs = MessageManager.getUserMessages(sender);
					for(MyMessage msg : userMsgs)
						MessageManager.deleteMessage(msg.getId());
				}
				
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

				MyMessage message = null;
				
				for(String key : keys) {
					message = new MyMessage(user.getEmail(), key, messageBody);
					MessageManager.sendMessage(message);
				}
				
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
