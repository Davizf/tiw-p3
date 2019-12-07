package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProductInCart;

@WebServlet(name = "ShoppingCart", urlPatterns = "/ShoppingCart")
public class ShoppingCart extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private ArrayList<ProductInCart> products = new ArrayList<ProductInCart>();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession();

		// Only if there is a user logged
		if(session.getAttribute("user") != null) {
			req.getSession().setAttribute("cartList", products);

			if(req.getParameter("type").equalsIgnoreCase("addToCart")) {

				int id = Integer.parseInt(req.getParameter("id"));
				int num = Integer.parseInt(req.getParameter("numOrder"));

				// Increase quantity if exists on cart
				for(ProductInCart product : products) {
					if(product.getProduct().getId() == id) {
						product.setQuantity(product.getQuantity() + num );
						RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
						rd.forward(req, res);
						return ;
					}
				}

				// Add new product to the cart
				ProductInCart newP = new ProductInCart(ProductController.getProduct(id));
				newP.setQuantity(num);
				products.add(newP);

				RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
				rd.forward(req, res);

			} else if(req.getParameter("type").equalsIgnoreCase("deleteInCart")) {
				// Remove 1 product
				int index = Integer.parseInt(req.getParameter("indexToRemove"));

				products.remove(index);

				RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
				rd.forward(req, res);
			} else if(req.getParameter("type").equalsIgnoreCase("modifyInCart")) {
				// Modify quantity
				int index = Integer.parseInt(req.getParameter("indexToModify"));

				int newQuantity = Integer.parseInt(req.getParameter("quantity"));

				products.get(index).setQuantity(newQuantity);;

				RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
				rd.forward(req, res);
			} else if(req.getParameter("type").equalsIgnoreCase("placeOrder")) {
				// Go to checkout
				RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
				rd.forward(req, res);
			}else if(req.getParameter("type").equalsIgnoreCase("checkout")){
				// Confirm order
				req.setAttribute("cartList", products);
				RequestDispatcher rd = req.getRequestDispatcher("order-confirm.jsp");
				rd.forward(req, res);
			}
		}else {
			RequestDispatcher rd = req.getRequestDispatcher("login-page.jsp");
			rd.forward(req, res);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// Get products list
		req.getSession().setAttribute("cartList", products);
		RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
		rd.forward(req, res);
	}

}
