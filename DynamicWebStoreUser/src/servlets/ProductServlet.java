package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.ProductController;
import model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = "/ProductServlet")
public class ProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		if(req.getParameter("op").equalsIgnoreCase("view")) {
			req.setAttribute("resultType", "showAll");

		}else if(req.getParameter("op").equalsIgnoreCase("search")) {
			String nameToQuery = req.getParameter("query");
			List<Product> products;
			// If only click on search, show all products
			if (nameToQuery.equals(""))
				products = ProductController.getAllProducts();
			else
				products = ProductController.getProductByName(nameToQuery);

			req.setAttribute("foundProducts", products);
			req.setAttribute("resultType", "foundByKey");

		}else if(req.getParameter("op").equalsIgnoreCase("category")) {
			String category = req.getParameter("category");
			String[] categories = category.split(",");

			List<Product> products;
			// Get products in a category
			if (categories.length == 1) {
				System.out.println(category);
				products = ProductController.getProductsByCategory(Integer.parseInt(category));
			} else {
				// Get products in categories
				List<Integer> idCategories=new ArrayList<Integer>(); 
				for (int i = 0; i < categories.length; i++)
					idCategories.add(Integer.parseInt(categories[i]));

				products = ProductController.getProductsByCategories(idCategories);
			}
			req.setAttribute("foundProducts", products);
			req.setAttribute("resultType", "foundByKey");

		}else if(req.getParameter("op").equalsIgnoreCase("filter")) {

			List<Product> products = null;
			List<Product> productsToCompare = null;
			List<Product> productsToElimine =  new ArrayList<>();

			// Get parametres
			String price = req.getParameter("chk_filter_price"), 
					stock = req.getParameter("chk_filter_stock"), shipPrice = req.getParameter("chk_filter_ship_price");
			// Convert to boolean
			boolean filterPrice = (price!=null && price.equals("on")),
					filterStock = (stock!=null && stock.equals("on")),
					filterShipPrice = (shipPrice!=null && shipPrice.equals("on"));

			// If no filters, just get all products
			if (!filterPrice && !filterStock && !filterShipPrice) {
				products = ProductController.getAllProducts();
			} else {
				// If multiple filters: remove products in products list

				// Filter by price
				if (filterPrice) {
					int filterMinimun=Integer.parseInt(req.getParameter("filter_price_minimun")),
							filterMaximum=Integer.parseInt(req.getParameter("filter_price_maximum"));

					String email = (String) ((HttpServletRequest) req).getSession().getAttribute("user");
					if (email==null || email.equals("")) {
						products = ProductController.getProductsBetweenPrices(filterMinimun,filterMaximum);
					} else {
						products = ProductController.getProductsBetweenSalePrices(filterMinimun,filterMaximum);
					}
				}

				// Filter by stock
				if (filterStock) {
					int filterStockMinumun=Integer.parseInt(req.getParameter("filter_stock_minimun"));
					if(products != null) {
						productsToCompare = ProductController.getProductsByStock(filterStockMinumun);
						for(Product product : products) {
							int idFound=contains(product, productsToCompare);
							if(idFound != -1) {
								productsToElimine.add(product);
							}
						}

						for(Product product : productsToElimine) {
							products.remove(product);
						}	
						productsToCompare = null;
						productsToElimine.clear();

					}else {
						products = ProductController.getProductsByStock(filterStockMinumun);
					}

				}

				// Filter by ship price
				if (filterShipPrice) {
					String freeShipping = req.getParameter("filter_free_shipping");
					boolean filterFreeShipping = (freeShipping!=null && freeShipping.equals("on"));
					if(filterFreeShipping) {
						if(products != null) {
							productsToCompare = ProductController.getProductsFreeShipment();
							for(Product product : products) {
								int idFound=contains(product, productsToCompare);
								if(idFound == -1) {
									productsToElimine.add(product);
								}
							}

							for(Product product : productsToElimine) {
								products.remove(product);
							}	
							productsToCompare = null;
							productsToElimine.clear();

						}else {
							products = ProductController.getProductsFreeShipment();
						}

					}
				}
			}

			req.setAttribute("foundProducts", products);
			req.setAttribute("resultType", "foundByKey");
		}

		RequestDispatcher rd = req.getRequestDispatcher("products.jsp");
		rd.forward(req, res);

	}

	private static int contains(Product product, List<Product> productsToCompare) {
		for (Product p : productsToCompare)
			if (p.getId() == product.getId())
				return p.getId();
		return -1;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}