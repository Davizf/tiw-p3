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
			Optional<Product> p = productDAO.findById(id);
			return new ResponseEntity<>(p, 
					(p.isPresent()) ? HttpStatus.OK : HttpStatus.NOT_FOUND
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
				List<Integer> categoriesList = new ArrayList<Integer>();

				String[] arr = categories.split(DELIMIT_CATEGORIES);
				for (int i = 0; i < arr.length; i++) categoriesList.add(Integer.parseInt(arr[i]));

				return getProductsByCategories(categoriesList, admin);
			}

			if (seller!=null) return getProductsBySeller(seller);

			if (productName!=null) return getProductByName(productName, admin);

			if (shipPrice!=null) return getProductsByShipPrice(shipPrice);

			if (stock!=null) return getProductsByStock(stock);

			List<Product> p = admin ? productDAO.findAll() : productDAO.findAllAvailable();

			return new ResponseEntity<>(p, 
					(p.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductsByCategoryId(Integer category, boolean admin){
		List<Product> p = admin ? productDAO.findAllByCategoryIdAdmin(category) : productDAO.findAllByCategoryId(category);
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK
				);
	}

	private ResponseEntity<?> getProductsByCategory(String category, boolean admin){
		List<Product> p = admin ? productDAO.findAllByCategoryAdmin(category) : productDAO.findAllByCategory(category);
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

	private ResponseEntity<?> getProductsByCategories(List<Integer> idCategories, boolean admin){
		if (idCategories.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		List<Product> p = admin ? productDAO.findAllByCategoriesIdAdmin(idCategories) : productDAO.findAllByCategoriesId(idCategories);

		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

	private ResponseEntity<?> getProductsBySeller(String email){
		List<Product> p = productDAO.findAllBySeller(email);
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

	@RequestMapping(value="products", params = "last", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getLastProducts(@RequestParam(value = "last", required = false) Integer quantity){
		try {
			if (quantity==null) quantity = new Integer(DEFAULT_LAST_PRODUCTS);
			if (quantity<0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Product> p = productDAO.findAllOrderById().subList(0, quantity);

			return new ResponseEntity<>(p, 
					(p.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
		try {
			Optional<Product> p = productDAO.findById(id);

			if (!p.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			p.get().setUserBean(null);
			productDAO.save(p.get());

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateProduct(@PathVariable(value = "id", required = true) Integer id, @RequestBody(required = true) Product product) {
		try {
			Optional<Product> p = productDAO.findById(id);

			if (!p.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			product.setId(id);
			productDAO.save(product);

			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products/{id}", params = "stock", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateProductStock(@PathVariable(value = "id", required = true) Integer id, @RequestParam(value = "stock", required = true) Integer stock) {
		try {
			Optional<Product> p = productDAO.findById(id);

			if (!p.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			p.get().setStock(stock);
			productDAO.save(p.get());

			return new ResponseEntity<>(p.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "products", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addProduct(@RequestBody(required = true) Product product) {
		try {
			productDAO.save(product);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductByName(String name, boolean admin){
		List<Product> p = admin ? productDAO.getProductByNameAdmin(name) : productDAO.getProductByName(name);
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

	@RequestMapping(value="products", params = {"min", "max"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsBetweenPrices(@RequestParam(value = "min", required = true) Integer min, @RequestParam(value = "max", required = true) Integer max){
		try {
			List<Product> p = productDAO.findAllBetweenPrices(new BigDecimal(min), new BigDecimal(max));
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products", params = {"sale_min", "sale_max"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findAllBetweenSalePrices(@RequestParam(value = "sale_min", required = true) Integer min, @RequestParam(value = "sale_max", required = true) Integer max){
		try {
			List<Product> p = productDAO.findAllBetweenSalePrices(new BigDecimal(min), new BigDecimal(max));
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> getProductsByShipPrice(Integer price){
		List<Product> p = (price==null) ? productDAO.findAllByShipment(new BigDecimal(0)) : productDAO.findAllByShipment(new BigDecimal(price));
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

	private ResponseEntity<?> getProductsByStock(Integer stock){
		List<Product> p = productDAO.findAllByStock(stock);
		return new ResponseEntity<>(p, 
				(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
				);
	}

}