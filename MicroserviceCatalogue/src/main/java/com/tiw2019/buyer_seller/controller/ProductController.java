package com.tiw2019.buyer_seller.controller;

import java.math.BigDecimal;
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

	private static final int  DEFAULT_LAST_PRODUCTS=4;

	@Autowired
	ProductDAO productDAO;

	@RequestMapping(value="products", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "admin", required = false) boolean admin){
		try {
			List<Product> p = admin ? productDAO.findAll() : productDAO.findAllAvailable();

			return new ResponseEntity<>(p, 
					(p.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

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

	@RequestMapping(value="products/category/{id}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsByCategoryId(@PathVariable(value = "id", required = true) Integer id, @RequestParam(value = "admin", required = false) boolean admin){
		try {
			List<Product> p = admin ? productDAO.findAllByCategoryIdAdmin(id) : productDAO.findAllByCategoryId(id);
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/category", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsByCategory(@RequestParam(value = "category", required = true) String category, @RequestParam(value = "admin", required = false) boolean admin){
		try {
			List<Product> p = admin ? productDAO.findAllByCategoryAdmin(category) : productDAO.findAllByCategory(category);
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/category", method=RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getProductsByCategories(@RequestBody(required = true) List<Integer> idCategories, @RequestParam(value = "admin", required = false) boolean admin){
		try {
			if (idCategories.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Product> p = admin ? productDAO.findAllByCategoriesIdAdmin(idCategories) : productDAO.findAllByCategoriesId(idCategories);

			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/seller/{seller}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsBySeller(@PathVariable(value = "seller", required = true) String email){
		try {
			List<Product> p = productDAO.findAllBySeller(email);
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/last", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getLastProducts(@RequestParam(value = "quantity", required = false) Integer quantity){
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

	@RequestMapping(value = "products/stock/{id}", method = RequestMethod.PUT, produces = "application/json")
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

	@RequestMapping(value="products/search", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductByName(@RequestParam(value = "name", required = true) String title, @RequestParam(value = "admin", required = false) boolean admin){
		try {
			List<Product> p = admin ? productDAO.getProductByNameAdmin(title) : productDAO.getProductByName(title);
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/search/between_prices", method=RequestMethod.GET, produces = "application/json")
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

	@RequestMapping(value="products/search/between_sale_prices", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findAllBetweenSalePrices(@RequestParam(value = "min", required = true) Integer min, @RequestParam(value = "max", required = true) Integer max){
		try {
			List<Product> p = productDAO.findAllBetweenSalePrices(new BigDecimal(min), new BigDecimal(max));
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/search/ship_price", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsByShipPrice(@RequestParam(value = "price", required = false) Integer price){
		try {
			List<Product> p;
			p = (price==null) ? productDAO.findAllByShipment(new BigDecimal(0)) : productDAO.findAllByShipment(new BigDecimal(price));
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="products/search/stock", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProductsByStock(@RequestParam(value = "stock", required = true) Integer stock){
		try {
			List<Product> p = productDAO.findAllByStock(stock);
			return new ResponseEntity<>(p, 
					(p.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}