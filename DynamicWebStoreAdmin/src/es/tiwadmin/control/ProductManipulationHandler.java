package es.tiwadmin.control;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.tiwadmin.manager.CategoryManager;
import es.tiwadmin.manager.ProductManager;
import es.tiwadmin.manager.UserManager;
import es.tiwadmin.model.User;
import es.tiwadmin.model.Product;
import es.tiwadmin.model.Category;

public class ProductManipulationHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null)
			return "/DynamicWebStoreAdmin";
		
		//Remove product
		if(request.getRequestURI().contains("Remove")) {
			ProductManager.deleteProduct(Integer.parseInt(request.getParameter("itemID")));
			
			return "/productList";
		}
		
		ArrayList<String> errors = new ArrayList<String>();
		
		//Product must belong to an existing user. If user doesn't exist, return to form again
		String sellerParam = request.getParameter("seller");
		User seller = UserManager.getUser(sellerParam);
		if(seller == null && !sellerParam.equals("[DELETED]"))
			errors.add("Seller must be an existing user");
		else {
			if(seller.getType() != 1)
				errors.add("Seller account must be registered with role seller");
		}
		
		//An existing category must be selected by user
		String catParam = request.getParameter("category");
		Category cat = null;
		if(catParam == null || catParam.equals("placeholder"))
			errors.add("A category must be selected");
		else {
			cat = CategoryManager.getCategory(Integer.parseInt(catParam));
			if(cat == null)
				errors.add("Category doesn't exist");
		}


		//Stock cannot be negative
		String stockParam = request.getParameter("stock");
		int stock = 0;
		
		if(stockParam.isEmpty())
			errors.add("Stock cannot be empty");
		else 
			if((stock = Integer.parseInt(stockParam)) < 0)
				errors.add("Stock cannot be a negative number");
		

		if(request.getParameter("name").isEmpty())				errors.add("Name cannot be empty");
		if(request.getParameter("short-description").isEmpty())	errors.add("Short description cannot be empty");
		if(request.getParameter("long-description").isEmpty())	errors.add("Description cannot be empty");


		if(errors.size() != 0) {
			request.setAttribute("errors", errors);
			return "WEB-INF/jsp/wrongParams.jsp";
		}
		
		//Create product or gets it from database
		String productId = request.getParameter("productId");
		Product newProduct = productId.isEmpty() ? new Product() : ProductManager.getProduct(Integer.parseInt(productId));
		String imagePathParam = request.getParameter("image");
		if(imagePathParam == null || imagePathParam.isEmpty())
			imagePathParam = "/DynamicWebStoreAdmin/images/box.png";
		
		
		//Fill new data
		newProduct.setName(request.getParameter("name"));
		newProduct.setShortDescription(request.getParameter("short-description"));
		newProduct.setDescription(request.getParameter("long-description"));
		newProduct.setCategoryBean(cat);
		newProduct.setStock(stock);
		newProduct.setImagePath(imagePathParam);
		newProduct.setUserBean(seller);

		String price = request.getParameter("price").replaceAll(",", ".");
		if(!price.contains(".")) price += ".0";
		
		newProduct.setPrice(new BigDecimal(price));
		newProduct.setSalePrice(new BigDecimal(price));
		newProduct.setShipPrice(new BigDecimal(price));
		
		/*
		//Image	
		try {
			Part imagePart = request.getPart("image");
			byte[] imageData = new byte[(int) imagePart.getSize()];
			imagePart.getInputStream().read(imageData, 0, imageData.length);
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(String.valueOf(Calendar.getInstance().getTimeInMillis()).getBytes());

	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        
	        String path = request.getServletContext().getRealPath("images");
			FileOutputStream imageFile = new FileOutputStream(path + sb.toString());
			imageFile.write(imageData);
			imageFile.flush();
			imageFile.close();
			
			newProduct.setImagePath("images/" + sb.toString());
			
		} catch(ServletException se) {
			se.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		*/
		
		//Create or update product
		if(productId.isEmpty())
			ProductManager.createProduct(newProduct);
		else
			ProductManager.updateProduct(newProduct);
		
		request.setAttribute("item", newProduct);
		return "WEB-INF/jsp/productDetail.jsp";
	}

}
