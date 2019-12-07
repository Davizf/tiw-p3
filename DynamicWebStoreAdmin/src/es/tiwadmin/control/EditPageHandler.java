package es.tiwadmin.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.tiwadmin.info.InformationProperties;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.User;

public class EditPageHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/tiw-admin";
		
		String wantedClass = this.getWantedClass(request);
		
		switch(wantedClass) {
			case "user":
				UserManager um = new UserManager(InformationProperties.getStrDatabaseName());
				request.setAttribute("item", um.getUser(request.getParameter("itemPK")));
				return "WEB-INF/jsp/" + getDestJspName(request) + ".jsp";
			case "product":
				ProductManager pm = new ProductManager(InformationProperties.getStrDatabaseName());
				request.setAttribute("item", pm.getProduct(Integer.parseInt(request.getParameter("itemPK"))));
				return "WEB-INF/jsp/" + getDestJspName(request) + ".jsp";
		}
		
		return "WEB-INF/jsp/404.jsp";
	}
	
	private String getDestJspName(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/") + 1);
	}
	
	private String getWantedClass(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/") + 1).replaceAll("Edit", "");
	}
}
