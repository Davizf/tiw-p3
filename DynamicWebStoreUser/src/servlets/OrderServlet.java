package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.BankController;
import controllers.OrderController;
import controllers.ProductController;
import controllers.UserController;
import model.Orders;
import model.Orders_has_Product;
import model.Transaction;
import model.ProductInCart;

@WebServlet(name = "Order", urlPatterns = "/Order")
public class OrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		ArrayList<ProductInCart> productsInCart = (ArrayList<ProductInCart>)session.getAttribute("cartList");

		if(req.getParameter("type").equalsIgnoreCase("confirm-checkout")) {
			
			Double price = Double.parseDouble(req.getParameter("total-price"));
			String cardNumber = req.getParameter("card");
			String expiryDate = req.getParameter("expiry");
			String cvv = req.getParameter("cvv");
			Transaction transaction = new Transaction(price, cardNumber, expiryDate, cvv);
			
			String transactionId = BankController.sendTransaction(transaction);
			
			if(transactionId.length() == 0) {
				RequestDispatcher rd = req.getRequestDispatcher("failure-page.jsp");
				rd.forward(req, res);
			}else {

				if(OrderController.checkProductsStock(productsInCart)) {
					// Create the order
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Orders order = new Orders();
					Orders_has_Product order_product;
					
					order.setConfirmation_id(transactionId);
					order.setAddress(req.getParameter("address"));
					order.setCity(req.getParameter("city"));
					order.setCountry(req.getParameter("country"));
					order.setPostalCode(Integer.parseInt(req.getParameter("zipCode")));
					order.setUserBean(UserController.getUser(email));
					order.setDate(formatter.format(date));

					// Fill the order with products
					ArrayList<Orders_has_Product> products = new ArrayList<Orders_has_Product>();
					for(ProductInCart product : productsInCart) {
						order_product = new Orders_has_Product();
						order_product.setProductPrice(product.getProduct().getSalePrice());
						order_product.setProductBean(product.getProduct());
						order_product.setOrder(order);
						order_product.setShipPrice(product.getProduct().getShipPrice());
						order_product.setQuantity(product.getQuantity());
						products.add(order_product);
						ProductController.updateStock(product.getProduct(), product.getQuantity());
					}
					order.setOrdersHasProducts(products);

					// Insert the order
					OrderController.createOrder(order);
					productsInCart.clear();
					session.setAttribute("cartList", null);
					RequestDispatcher rd = req.getRequestDispatcher("confirm-page.jsp");
					rd.forward(req, res);
				} else {
					req.setAttribute("msg_error", "Check the quantity of your products, not enough stock.");
					RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
					rd.forward(req, res);
				}
			}
			
			

		}else if(req.getParameter("type").equalsIgnoreCase("my-orders")) {
			RequestDispatcher rd = req.getRequestDispatcher("my-orders.jsp");
			rd.forward(req, res);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}