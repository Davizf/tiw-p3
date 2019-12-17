package com.tiw2019.buyer_seller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.OrderDAO;
import com.tiw2019.buyer_seller.dao.Orders_has_ProductDAO;
import com.tiw2019.buyer_seller.model.Order;
import com.tiw2019.buyer_seller.model.Orders_has_Product;

@RestController
public class OrderController {

	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	Orders_has_ProductDAO ohpDAO;
	
	@RequestMapping(value="/order", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<?> createOrder(@RequestBody(required=true) Order order) {
		try {
			for(Orders_has_Product ohp : order.getOrdersHasProducts())
				ohp.setOrder(order);

			orderDAO.save(order);		
			ohpDAO.saveAll(order.getOrdersHasProducts());
			
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/order", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getOrders() {
		try {
			List<Order> orders = orderDAO.findAll();
			return new ResponseEntity<List<Order>>(orders, (orders != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/order/{email}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<?> getOrdersByUser(@PathVariable(value="email", required=true) String email) {
		try {
			List<Order> orders = orderDAO.findAllByEmail(email);
			return new ResponseEntity<List<Order>>(orders, (orders != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="/order", params = {"product_id"}, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<?> getOrdersByProduct(@RequestParam(value="product_id", required=true) Integer productId) {
		try {
			List<Order> orders = orderDAO.findAllByProduct(productId);
			return new ResponseEntity<List<Order>>(orders, (orders != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
