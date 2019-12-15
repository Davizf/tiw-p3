package servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controllers.CategoryController;
import controllers.ProductController;
import controllers.UserController;
import model.Product;

@WebServlet(name = "Catalogue", urlPatterns = "/Catalogue")
@MultipartConfig
public class CatalogueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String IMAGE_FOLDER = "/images";

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession sesion = req.getSession();
		String user = (String) sesion.getAttribute("user");

		if (req.getParameter("type").equalsIgnoreCase("change-stock")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product p = ProductController.getProduct(id);
			int newStock = Integer.parseInt(req.getParameter("newStock"));

			// Modify stock (if user is correct for security)
			if (ProductController.verifyStock(newStock)) {
				p.setStock(newStock);
				if (p.getUserBean().getEmail().equals(user))
					ProductController.modifyProduct(p);
			} else {
				req.setAttribute("msg_error", "Error, invalid stock value.");
			}

			RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
			rd.forward(req, res);

		} else if (req.getParameter("type").equalsIgnoreCase("add")) {
			req.setAttribute("operation", "add");
			req.setAttribute("seller_user", req.getParameter("seller_user"));
			RequestDispatcher rd = req.getRequestDispatcher("product-page-seller.jsp");
			rd.forward(req, res);

		} else if (req.getParameter("type").equalsIgnoreCase("modify")) {
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("productId", id);
			req.setAttribute("seller_user", req.getParameter("seller_user"));

			// Redirect to page for modify
			Product p = ProductController.getProduct(id);
			req.setAttribute("product", p);
			req.setAttribute("operation", "modify");
			RequestDispatcher rd = req.getRequestDispatcher("product-page-seller.jsp");
			rd.forward(req, res);

		} else if (req.getParameter("type").equalsIgnoreCase("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product p = ProductController.getProduct(id);

			// Delete product (if user is correct for security)
			if (p.getUserBean().getEmail().equals(user))
				ProductController.deleteProduct(id);

			RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
			rd.forward(req, res);
		} else if (req.getParameter("type").equalsIgnoreCase("add_product")) {
			// Take data
			String product_name = req.getParameter("product_name");
			double product_price = Double.parseDouble(req.getParameter("product_price"));
			double product_sale_price = Double.parseDouble(req.getParameter("product_sale_price"));
			double product_ship_price = Double.parseDouble(req.getParameter("product_ship_price"));
			int product_stock = Integer.parseInt(req.getParameter("product_stock"));
			String product_short_description = req.getParameter("product_short_description");
			String product_description = req.getParameter("product_description");
			String product_category = req.getParameter("product_category");
			String product_user = req.getParameter("product_user");

			// Verify if all attributes are correct
			String verify = verifyNewData(product_name, product_price, product_sale_price, product_ship_price,
					product_stock, product_short_description, product_description, product_category);
			if (!verify.equals("")) {
				req.setAttribute("msg_error", verify);
				RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
				rd.forward(req, res);
				return;
			}

			// Auxiliar vars for image paths
			String product_image_path;
			Part product_image = req.getPart("product_image_file");
			String image_fileName = extractFileName(product_image);
			String image_parentPath = getServletContext().getRealPath(IMAGE_FOLDER),
					image_filePath = "newProduct_" + new Date().getTime() + ".png",
					image_parentShortPath = req.getContextPath() + IMAGE_FOLDER,
					image_completePath = image_parentPath + "/" + image_filePath,
					image_completeShortPath = image_parentShortPath + "/" + image_filePath;
			File image_file;

			if (image_fileName.equals("")) {// There isnt new image
				req.setAttribute("msg_error", "Error, select an image.");
				RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
				rd.forward(req, res);
				return;
			} else {
				// Copy image to folder
				image_file = new File(new File(image_parentPath), image_filePath);

				if (image_file.exists())
					image_file.delete();
				try (InputStream input = product_image.getInputStream()) {
					Files.copy(input, image_file.toPath());
				}
				product_image_path = image_completeShortPath;
			}

			// Sets attributes to the new product
			Product p = new Product();
			p.setName(product_name);
			p.setPrice(new BigDecimal(product_price));
			p.setSalePrice(new BigDecimal(product_sale_price));
			p.setShipPrice(new BigDecimal(product_ship_price));
			p.setStock(product_stock);
			p.setShortDescription(product_short_description);
			p.setCategoryBean(CategoryController.getCategory(product_category));
			p.setDescription(product_description);
			p.setImagePath(product_image_path);
			p.setUserBean(UserController.getUser(product_user));

			// Add
			int newId = ProductController.addProduct(p);
			if (newId == -1) {
				req.setAttribute("msg_error", "Error adding a product.");
				RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
				rd.forward(req, res);
				return;
			}
			// Change name of file to id
			image_completePath = image_parentPath + "/" + image_filePath;
			image_completeShortPath = image_parentShortPath + "/" + image_filePath;

			// Change image path auxiliar for the definitive
			File image_file2 = new File(image_completePath);
			image_file.renameTo(image_file2);
			p.setId(newId);
			p.setImagePath(image_completeShortPath);
			ProductController.modifyProduct(p);

			RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
			rd.forward(req, res);
		} else if (req.getParameter("type").equalsIgnoreCase("modify_product")) {
			// Take data
			int product_id = Integer.parseInt(req.getParameter("product_id"));
			String product_name = req.getParameter("product_name");
			double product_price = Double.parseDouble(req.getParameter("product_price"));
			double product_sale_price = Double.parseDouble(req.getParameter("product_sale_price"));
			double product_ship_price = Double.parseDouble(req.getParameter("product_ship_price"));
			int product_stock = Integer.parseInt(req.getParameter("product_stock"));
			String product_short_description = req.getParameter("product_short_description");
			String product_description = req.getParameter("product_description");
			String product_category = req.getParameter("product_category");

			// Verify if all attributes are correct
			String verify = verifyNewData(product_name, product_price, product_sale_price, product_ship_price,
					product_stock, product_short_description, product_description, product_category);
			if (!verify.equals("")) {
				req.setAttribute("msg_error", verify);
				RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
				rd.forward(req, res);
				return;
			}

			// Auxiliar vars for image paths
			String product_image_path;
			Part product_image = req.getPart("product_image_file");
			String image_fileName = extractFileName(product_image),
					image_parentPath = getServletContext().getRealPath(IMAGE_FOLDER),
					image_filePath = product_id + ".png", image_parentShortPath = req.getContextPath() + IMAGE_FOLDER,
					image_completeShortPath = image_parentShortPath + "/" + image_filePath;
			File image_file;

			if (image_fileName.equals("")) {// There isnt new image
				product_image_path = req.getParameter("product_image_path");
			} else {
				// Copy image to folder
				image_file = new File(new File(image_parentPath), image_filePath);
				if (image_file.exists())
					image_file.delete();
				try (InputStream input = product_image.getInputStream()) {
					Files.copy(input, image_file.toPath());
				}
				product_image_path = image_completeShortPath;
			}

			// Sets attributes to the new product
			Product p = ProductController.getProduct(product_id);
			p.setId(product_id);
			p.setName(product_name);
			p.setPrice(new BigDecimal(product_price));
			p.setSalePrice(new BigDecimal(product_sale_price));
			p.setShipPrice(new BigDecimal(product_ship_price));
			p.setStock(product_stock);
			p.setShortDescription(product_short_description);
			p.setCategoryBean(CategoryController.getCategory(product_category));
			p.setDescription(product_description);
			p.setImagePath(product_image_path);
			p.setUserBean(UserController.getUser(user));

			ProductController.modifyProduct(p);
			RequestDispatcher rd = req.getRequestDispatcher("catalogue.jsp");
			rd.forward(req, res);
		}
	}

	private String verifyNewData(String name, double price, double sale_price, double ship_price, int stock,
			String short_description, String description, String category) {
		String resul = "";
		if (name.length() > ProductController.NAME_CHARACTER) {
			resul = "Error. Name must have less than " + ProductController.NAME_CHARACTER + " characters";
		} else if (price < 0) {
			resul = "Error. Price must be greater than 0";
		}
		if (sale_price < 0) {
			resul = "Error. Sale price must be greater than 0";
		}
		if (ship_price < 0) {
			resul = "Error. Ship price must be greater than 0";
		}
		if (stock < 0) {
			resul = "Error. Stock must be greater than 0";
		}
		if (short_description.length() > ProductController.SHORT_DESC_CHARACTER) {
			resul = "Error. Short description must have less than " + ProductController.SHORT_DESC_CHARACTER
					+ " characters";
		}
		if (category.equals("")) {
			resul = "Error, select a category.";
		}
		return resul;
	}

	private static String extractFileName(Part part) {
		String content = part.getHeader("content-disposition");
		String[] items = content.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return null;
	}

}