package com.tiw2019.buyer_seller.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	public static final int MAX_STOCK=2147483647, MIN_STOCK=0, NAME_CHARACTER=100, SHORT_DESC_CHARACTER=300;

	@Autowired
	ProductDAO productDAO;

	@RequestMapping(value="products/verify_stock/{stock}", method=RequestMethod.GET, produces = "application/json")
	public static boolean verifyStock(@PathVariable(value = "stock", required = true) int stock) {
		return stock<MAX_STOCK && stock>MIN_STOCK;
	}

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
					(p.isPresent()) ? HttpStatus.OK : HttpStatus.NO_CONTENT
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
	public ResponseEntity<?> getProductsByCategory(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "admin", required = false) boolean admin){
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
			if (quantity==null || quantity<0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Product> p = productDAO.findAllOrderById().subList(0, quantity);

			return new ResponseEntity<>(p, 
					(p.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}

/* OPERATIONS
	 List<Product> getAllProducts(){
	public List<Product> getAllAvailableProducts() { Product.findAllAvailable

	 boolean deleteProduct(int id) {

	 boolean modifyProduct(Product p) {
	 void updateStock(Product product, int quantity) {

	 int addProduct(Product p) {

	 List<Product> getProductByName(String title){
	 List<Product> getProductsBetweenPrices(int min, int max){
	 List<Product> getProductsBetweenSalePrices(int min, int max){
	 List<Product> getProductsByShipPrice(int price) {
	 List<Product> getProductsFreeShipment(){
	 List<Product> getProductsByStock(int min){
 */