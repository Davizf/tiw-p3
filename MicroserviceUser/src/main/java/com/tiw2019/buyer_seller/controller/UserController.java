package com.tiw2019.buyer_seller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.UserDAO;
import com.tiw2019.buyer_seller.model.User;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="users", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUsers(){
		try {
			List<User> users = userDAO.findAll();
			return new ResponseEntity<>(users, 
					(users.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			System.out.println("---------" + ex.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
