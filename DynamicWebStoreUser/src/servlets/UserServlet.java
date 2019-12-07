package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserController;
import model.User;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	ArrayList<User> users = new ArrayList<User>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String op = req.getParameter("operation");
		if (op != null && op.equalsIgnoreCase("log_out"))
			req.getSession().setAttribute("user", null);
		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		rd.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		String email = req.getParameter("email");	


		if(req.getParameter("operation").equalsIgnoreCase("Register")) {

			if(UserController.checkUserEmail(email)) {
				req.setAttribute("message", "This email has already been taken!");
				RequestDispatcher rd = req.getRequestDispatcher("register-page.jsp");
				rd.forward(req, res);
			} else {
				// Create the user
				User user = new User();
				user.setPhone(Integer.parseInt(req.getParameter("tel")));
				user.setPostalCode(Integer.parseInt(req.getParameter("zipCode")));
				user.setAddress(req.getParameter("address"));
				user.setCity(req.getParameter("city"));
				user.setCountry(req.getParameter("country"));
				user.setEmail(email);
				user.setName(req.getParameter("firstName"));
				user.setSurnames(req.getParameter("lastName"));
				user.setPassword(req.getParameter("password"));
				user.setCreditCard(req.getParameter("card"));
				user.setCreditCardExpiration(req.getParameter("cardExpire"));
				user.setCredit_card_CVV(Integer.parseInt(req.getParameter("cvv")));
				String seller = req.getParameter("seller");
				user.setType( (byte) ((seller != null && seller.equals("on"))?1:0) );

				// Insert the user
				UserController.addUser(user);

				RequestDispatcher rd = req.getRequestDispatcher("login-page.jsp");
				rd.forward(req, res);
			}
		} else if(req.getParameter("operation").equalsIgnoreCase("Login")) {
			String password = req.getParameter("password");

			// Check pass
			if(UserController.verifyUser(email, password)) {
				User user = UserController.getUserInformation(email);
				session.setAttribute("user", email);
				session.setAttribute("username", user.getName());
				session.setAttribute("cartList", null);

				RequestDispatcher rd;
				// If user is seller go to catalogue
				if (user.getType() == UserController.USER_TYPE_SELLER) {
					rd = req.getRequestDispatcher("catalogue.jsp");
				} else {
					rd = req.getRequestDispatcher("index.jsp");
				}
				rd.forward(req, res);
			} else {
				req.setAttribute("message", "The email or password is wrong!");
				RequestDispatcher rd = req.getRequestDispatcher("login-page.jsp");
				rd.forward(req, res);
			}

		}  else if(req.getParameter("operation").equalsIgnoreCase("Save my profile")) {
			// Create the user
			User user = new User();
			user.setPhone(Integer.parseInt(req.getParameter("tel")));
			user.setPostalCode(Integer.parseInt(req.getParameter("zipCode")));
			user.setAddress(req.getParameter("address"));
			user.setCity(req.getParameter("city"));
			user.setCountry(req.getParameter("country"));
			user.setEmail(email);
			user.setName(req.getParameter("firstName"));
			user.setSurnames(req.getParameter("lastName"));
			user.setPassword(req.getParameter("password"));
			user.setCreditCard(req.getParameter("card"));
			user.setCreditCardExpiration(req.getParameter("cardExpire"));
			user.setCredit_card_CVV(Integer.parseInt(req.getParameter("cvv")));
			session.setAttribute("username", user.getName());

			// Modify the user
			UserController.modifyUser(user);

			req.setAttribute("message", "Changes have been made correctly!");
			req.setAttribute("user_information", (User) user);
			RequestDispatcher rd = req.getRequestDispatcher("profile.jsp");
			rd.forward(req, res);

		} else if(req.getParameter("operation").equalsIgnoreCase("No")) {
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, res);

		} else if(req.getParameter("operation").equalsIgnoreCase("Yes")) {
			// Delete the user
			User user = UserController.getUserInformation((String) session.getAttribute("user"));

			UserController.deleteUser(user);

			session.invalidate();
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, res);
		}
	}

}
