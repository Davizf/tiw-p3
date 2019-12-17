package es.tiwadmin.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.tiwadmin.manager.MessageManager;
import es.tiwadmin.manager.OrderManager;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.MyMessage;
import es.tiwadmin.model.Order;
import es.tiwadmin.model.User;

public class ListPageHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/DynamicWebStoreAdmin";
		
		String wantedClass = this.getWantedClass(request);
		
		switch(wantedClass)  {
			case "user":
				request.setAttribute("items", UserManager.findAllUsers());
				return "WEB-INF/jsp/userList.jsp";
			case "product":
				String userParam = request.getParameter("itemPK");
				if(userParam == null || userParam.isEmpty()) {
					request.setAttribute("items", ProductManager.findAllProducts());
				} else 
					request.setAttribute("items", UserManager.getUser(userParam).getProducts());
				return "WEB-INF/jsp/productList.jsp";
			case "message":
//				HttpSession session = request.getSession();
//				MessageManager mm = new MessageManager();
//				List<MessageCollection> newMessages = mm.readJMS(user.getEmail());
//				
//				@SuppressWarnings("unchecked")
//				List<MessageCollection> sessionMsgs = (List<MessageCollection>) session.getAttribute("messages");
//				
//				session.setAttribute("messages", mm.join(sessionMsgs, newMessages));
				
				request.setAttribute("messages", MessageManager.getUserMessages(user.getEmail()));
				
				
				
				
				
				return "WEB-INF/jsp/messageList.jsp";
			case "order":
				//OrderManager om = new OrderManager(InformationProperties.getStrDatabaseName());
				List<Order> list = OrderManager.findAllOrders();
				request.setAttribute("items", list);
				return "WEB-INF/jsp/orderList.jsp";
		}
		
		return "WEB-INF/jsp/404.jsp";
	}
	
	private String getWantedClass(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/") + 1).replaceAll("List", "");
	}

}
