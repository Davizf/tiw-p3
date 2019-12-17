package com.tiw2019.buyer_seller.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.ProductDAO;
import com.tiw2019.buyer_seller.model.Product;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class ProductController {

	private static final int DEFAULT_LAST_PRODUCTS=4;
	private static final String DELIMIT_CATEGORIES=",";

	@Autowired
	ProductDAO productDAO;

	@RequestMapping(value="products/{id}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProduct(@PathVariable(value = "id", required = true) Integer id){
		try {
			Optional<Product> products = productDAO.findById(id);
			return new ResponseEntity<>(products, 
					(products.isPresent()) ? HttpStatus.OK : HttpStatus.NOT_FOUND
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProducts(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "admin", required = false) boolean admin,
			@RequestParam(value = "category_id", required = false) Integer categoryId, @RequestParam(value = "categories", required = false) String categories,
			@RequestParam(value = "seller", required = false) String seller, @RequestParam(value = "product_name", required = false) String productName,
			@RequestParam(value = "ship_price", required = false) Integer shipPrice, @RequestParam(value = "stock", required = false) Integer stock){
		try {
			if (category!=null) return getProductsByCategory(category, admin);

			if (categoryId!=null) return getProductsByCategoryId(categoryId, admin);

			if (categories!=null) {
				// Convert "id, id" to List<Integer>
				List<Integer> categoryList = new ArrayList<Integer>();

				String[] arr = categories.split(DELIMIT_CATEGORIES);
				for (int i = 0; i < arr.length; i++) categoryList.add(Integer.parseInt(arr[i]));

				return getProductsByCategories(categoryList, admin);
			}

			if (seller!=null) return getProductsBySeller(seller);

			if (productName!=null) return getProductByName(productName, admin);

			if (shipPrice!=null) return getProductsByShipPrice(shipPrice);

			if (stock!=null) return getProductsByStock(stock);

			List<Product> products = admin ? productDAO.findAll() : productDAO.findAllAvailable();

			if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductsByCategoryId(Integer category, boolean admin){
		List<Product> products = admin ? productDAO.findAllByCategoryIdAdmin(category) : productDAO.findAllByCategoryId(category);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	private ResponseEntity<?> getProductsByCategory(String category, boolean admin){
		List<Product> products = admin ? productDAO.findAllByCategoryAdmin(category) : productDAO.findAllByCategory(category);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	private ResponseEntity<?> getProductsByCategories(List<Integer> idCategories, boolean admin){
		if (idCategories.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		List<Product> products = admin ? productDAO.findAllByCategoriesIdAdmin(idCategories) : productDAO.findAllByCategoriesId(idCategories);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	private ResponseEntity<?> getProductsBySeller(String email){
		List<Product> products = productDAO.findAllBySeller(email);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@RequestMapping(value="products", params = "last", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getLastProducts(@RequestParam(value = "last", required = false) Integer quantity){
		try {
			if (quantity==null) quantity = new Integer(DEFAULT_LAST_PRODUCTS);
			if (quantity<0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Product> products = productDAO.findAllOrderById().subList(0, quantity);

			if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
		try {
			Optional<Product> product = productDAO.findById(id);

			if (!product.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			product.get().setUserBean(null);
			productDAO.save(product.get());

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> updateProduct(@PathVariable(value = "id", required = true) Integer id, @RequestBody(required = true) Product product) {
		try {
			Optional<Product> p = productDAO.findById(id);

			if (!p.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			product.setId(id);
			productDAO.save(product);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products/{id}", params = "stock", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> updateProductStock(@PathVariable(value = "id", required = true) Integer id, @RequestParam(value = "stock", required = true) Integer stock) {
		try {
			Optional<Product> product = productDAO.findById(id);

			if (!product.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			product.get().setStock(stock);
			productDAO.save(product.get());

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addProduct(@RequestBody(required = true) Product product) {
		try {
			productDAO.save(product);
			return new ResponseEntity<Integer>(product.getId(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductByName(String name, boolean admin){
		List<Product> products = admin ? productDAO.getProductByNameAdmin(name) : productDAO.getProductByName(name);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@RequestMapping(value="products", params = {"min", "max"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsBetweenPrices(@RequestParam(value = "min", required = true) Integer min, @RequestParam(value = "max", required = true) Integer max){
		try {
			List<Product> products = productDAO.findAllBetweenPrices(new BigDecimal(min), new BigDecimal(max));

			if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products", params = {"sale_min", "sale_max"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findAllBetweenSalePrices(@RequestParam(value = "sale_min", required = true) Integer min, @RequestParam(value = "sale_max", required = true) Integer max){
		try {
			List<Product> products = productDAO.findAllBetweenSalePrices(new BigDecimal(min), new BigDecimal(max));

			if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductsByShipPrice(Integer price){
		List<Product> products = (price==null) ? productDAO.findAllByShipment(new BigDecimal(0)) : productDAO.findAllByShipment(new BigDecimal(price));

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	private ResponseEntity<?> getProductsByStock(Integer stock){
		List<Product> products = productDAO.findAllByStock(stock);

		if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

}