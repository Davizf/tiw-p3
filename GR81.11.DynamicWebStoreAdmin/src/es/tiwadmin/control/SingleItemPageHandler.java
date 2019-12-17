package es.tiwadmin.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.tiwadmin.manager.CategoryManager;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.User;

public class SingleItemPageHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/DynamicWebStoreAdmin";
		
		if(request.getMethod().equals("GET")) {
			switch(getEndpoint(request)) {
				case "profile":
					request.setAttribute("item", user);
					return "WEB-INF/jsp/userDetail.jsp";
				case "userAdd":
					return "WEB-INF/jsp/userForm.jsp";
				case "productAdd":
					request.setAttribute("categoryList", CategoryManager.findAllCategories());
					return "WEB-INF/jsp/productForm.jsp";
				default:
					return "/DynamicWebStoreAdmin";
			}
		}
		
		String wantedClass = this.getWantedClass(request);
		
		switch(wantedClass) {
			case "user":
				request.setAttribute("item", UserManager.getUser(request.getParameter("itemPK")));
				if(getEndpoint(request).contains("Detail"))
					return "WEB-INF/jsp/userDetail.jsp";
				else
					return "WEB-INF/jsp/userForm.jsp";
				
			case "product":
				request.setAttribute("item", ProductManager.getProduct(Integer.parseInt(request.getParameter("itemID"))));
				if(getEndpoint(request).contains("Detail"))
					return "WEB-INF/jsp/productDetail.jsp";
				else {
					request.setAttribute("categoryList", CategoryManager.findAllCategories());
					return "WEB-INF/jsp/productForm.jsp";
				}
				
			case "message":
				request.setAttribute("receivers", request.getParameter("itemPK"));
				return "WEB-INF/jsp/messageForm.jsp";
				
				
		}
		
		return "WEB-INF/jsp/404.jsp";
	}
	
	private String getEndpoint(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/") + 1);
	}
	
	private String getWantedClass(HttpServletRequest request) {
		String url = request.getRequestURI();
		url = url.substring(url.lastIndexOf("/") + 1);
		return url.equals("profile") ? "profile" : url.replaceFirst("[A-Z][a-z]+", "");
	}

}
