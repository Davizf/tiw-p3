package com.tiw2019.buyer_seller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.WhishListDAO;
import com.tiw2019.buyer_seller.model.WishList;


@RestController
public class WishListController {

	@Autowired
	WhishListDAO wishListDAO;
	
	@RequestMapping(value="/wishlist", params = {"user_email"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getWishListByEmail(@RequestParam(value="user_email", required=true) String email) {
		try {
			List<WishList> wl = wishListDAO.findByEmail(email);
			return new ResponseEntity<List<WishList>>(wl,
				(wl != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/wishlist", params = {"user_email", "product_id"}, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getWishListByEmailAndProduct(
			@RequestParam(value="user_email", required=true) String email,
			@RequestParam(value="product_id", required=true) Integer productId) {
		try {
			WishList wl = wishListDAO.findByEmailAndProduct(email, productId);
			return new ResponseEntity<WishList>(wl,
				(wl != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/wishlist", method=RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addToWishList(@RequestBody(required=true) WishList wishList) {
		try {
			wishListDAO.save(wishList);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/wishlist/{wl_id}", method=RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteFromWishList(@PathVariable(value="wl_id", required=true) Integer id) {
		try {
			wishListDAO.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
