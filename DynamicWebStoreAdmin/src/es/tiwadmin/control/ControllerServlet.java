package es.tiwadmin.control;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/logout",
	"/userList",
	"/productList",
	"/userDetail",
	"/productDetail",
	"/profile",
	"/userEdit",
	"/productEdit",
	"/userSave",
	"/userAdd",
	"/userRemove",
	"/productAdd",
	"/productRemove",
	"/productSave",
	"/massOperation",
	"/messageList",
	"/messageWrite",
	"/orderList"
})

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HashMap<String, RequestHandler> handlerList;
	
	public void init(ServletConfig config) throws ServletException {
		this.handlerList = new HashMap<String, RequestHandler>();
		
		this.handlerList.put("/tiw-admin", new LoginHandler());
		this.handlerList.put("/logout", new LoginHandler());
		this.handlerList.put("/userList", new ListPageHandler());
		this.handlerList.put("/productList", new ListPageHandler());
		this.handlerList.put("/userDetail", new SingleItemPageHandler());
		this.handlerList.put("/productDetail", new SingleItemPageHandler());
		this.handlerList.put("/profile", new SingleItemPageHandler());
		this.handlerList.put("/userEdit", new SingleItemPageHandler());
		this.handlerList.put("/productEdit", new SingleItemPageHandler());
		this.handlerList.put("/userSave", new UserManipulationHandler());
		this.handlerList.put("/userAdd", new SingleItemPageHandler());
		this.handlerList.put("/userRemove", new UserManipulationHandler());
		this.handlerList.put("/productAdd", new SingleItemPageHandler());
		this.handlerList.put("/productRemove", new ProductManipulationHandler());
		this.handlerList.put("/productSave", new ProductManipulationHandler());
		this.handlerList.put("/massOperation", new MassOperationHandler());
		this.handlerList.put("/messageList", new ListPageHandler());
		this.handlerList.put("/orderList", new ListPageHandler());
		this.handlerList.put("/messageWrite", new SingleItemPageHandler());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler rh = (RequestHandler) handlerList.get(request.getServletPath());
		
		if(rh == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			//request.getRequestDispatcher("WEB-INF/jsp/404.jsp").forward(request, response);
			return;
		}
		String responsePath = rh.handleRequest(request, response);
		if(responsePath != null)
			request.getRequestDispatcher(responsePath).forward(request, response);
	}

}
