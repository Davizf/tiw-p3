package es.tiwadmin.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.tiwadmin.info.InformationProperties;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.User;

public class LoginHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			String url = request.getRequestURI();
			if(url.substring(url.lastIndexOf("/") + 1).equals("logout")) {
				session.invalidate();
				try {
					response.sendRedirect("/GR81.11.DynamicWebStoreAdmin");
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
				return null;
			}
			
			return "/userList";
		} else {
			String actionParam = request.getParameter("action");
			if(actionParam != null && actionParam.equals("login")) {
				String email = request.getParameter("email");
			
				if(UserManager.verifyAdmin(email, request.getParameter("password"))) {
					session.setAttribute("user", UserManager.getUser(email));
					return "/userList";
				} else
					return "WEB-INF/jsp/login.jsp";
			}
			
			return "WEB-INF/jsp/login.jsp";
		}
	}
}
